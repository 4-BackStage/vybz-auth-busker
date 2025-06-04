package back.vybz.auth_busker.busker.application;

import back.vybz.auth_busker.busker.common.entity.BaseResponseStatus;
import back.vybz.auth_busker.busker.common.exception.BaseException;
import back.vybz.auth_busker.busker.common.util.RedisUtil;
import back.vybz.auth_busker.busker.dto.SendPurpose;
import back.vybz.auth_busker.busker.dto.request.RequestSendEmailCodeDto;
import back.vybz.auth_busker.busker.dto.request.RequestVerificationEmailDto;
import back.vybz.auth_busker.busker.infrastructure.email.EmailSender;
import back.vybz.auth_busker.busker.infrastructure.email.EmailTemplateBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final RedisUtil<String> redisUtil;

    private final AuthService authService;

    private final EmailSender emailSender;

    private final EmailTemplateBuilder emailTemplateBuilder;

    @Override
    public void sendEmailCode(RequestSendEmailCodeDto requestSendEmailCodeDto) {

        if (requestSendEmailCodeDto.getPurpose() == SendPurpose.FIND_PASSWORD &&
                authService.loadBuskerByUuid(requestSendEmailCodeDto.getEmail()) == null
        ) {
            throw new BaseException(BaseResponseStatus.NOT_FOUND_EMAIL);
        }

        final String code = RandomStringUtils.random(6, true, true);
        final String email = requestSendEmailCodeDto.getEmail();

        final String limitKey = "Limit:EmailSend:" + email;

        if (redisUtil.get(limitKey) != null) {
            throw new BaseException(BaseResponseStatus.EMAIL_CODE_SEND_LIMITED);
        }

        redisUtil.save(email, code, 5L, TimeUnit.MINUTES);
        redisUtil.save(limitKey, "3", 3L, TimeUnit.MINUTES);

        if (requestSendEmailCodeDto.getPurpose() == SendPurpose.FIND_EMAIL || requestSendEmailCodeDto.getPurpose() == SendPurpose.FIND_PASSWORD) {
            emailSender.send(email, "VYBZ 이메일 인증", emailTemplateBuilder.buildVerificationEmail("이메일 인증을", code));
        } else {
            // 회원가입일 경우
            emailSender.send(email, "VYBZ 회원가입 인증", emailTemplateBuilder.buildVerificationEmail("회원가입 인증을", code));
        }

        redisUtil.save("EmailVerify:" + email, "0", 5L, TimeUnit.MINUTES);
    }

    @Override
    public void verifyEmailCode(RequestVerificationEmailDto requestVerificationEmailDto) {
        final String email = requestVerificationEmailDto.getEmail();
        final String redisCode = redisUtil.get(email);

        if (redisCode == null) {
            throw new BaseException(BaseResponseStatus.EXPIRED_EMAIL_CODE);
        }

        if (!redisCode.equals(requestVerificationEmailDto.getVerificationCode())) {
            String failKey = "EmailVerify:" + email;

            if (redisUtil.increase(failKey, 5L, TimeUnit.MINUTES) >= 5) {
                redisUtil.delete(email);
                redisUtil.delete(failKey);
                throw new BaseException(BaseResponseStatus.EMAIL_CODE_VERIFICATION_LIMITED);
            }
            throw new BaseException(BaseResponseStatus.INVALID_EMAIL_CODE);
        }

        if (requestVerificationEmailDto.getPurpose() == SendPurpose.FIND_PASSWORD) {
            redisUtil.save("find-password-Verified:" + email, "true", 10, TimeUnit.MINUTES);
        } else if (requestVerificationEmailDto.getPurpose() == SendPurpose.SIGN_UP) {
            redisUtil.save("sign-up-Verified:" + email, "true", 20, TimeUnit.MINUTES);
        } else {
            redisUtil.save("find-email-Verified:" + email, "true", 10, TimeUnit.MINUTES);
        }

        redisUtil.delete(email);
        redisUtil.delete("EmailVerify:" + email);
    }
}
