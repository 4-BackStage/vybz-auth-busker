package back.vybz.auth_busker.busker.application;

import back.vybz.auth_busker.busker.util.VerificationKeyManager;
import back.vybz.auth_busker.common.entity.BaseResponseStatus;
import back.vybz.auth_busker.common.exception.BaseException;
import back.vybz.auth_busker.common.util.RedisUtil;
import back.vybz.auth_busker.busker.dto.SendPurpose;
import back.vybz.auth_busker.busker.dto.request.RequestSendSmsCodeDto;
import back.vybz.auth_busker.busker.dto.request.RequestVerificationSmsDto;
import back.vybz.auth_busker.busker.util.SmsCertificationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {

    private final SmsCertificationUtil smsCertificationUtil;

    private final RedisUtil<String> redisUtil;

    @Override
    public void sendSms(RequestSendSmsCodeDto requestSmsDto) {

        String certificationCode = createCertificationCode();

        String phoneNumber = requestSmsDto.getPhoneNumber();
        SendPurpose purpose = requestSmsDto.getSendPurpose();

        String textMessage = switch (purpose) {
            case FIND_EMAIL -> "[VYBZ] 이메일 찾기 인증번호 " + certificationCode + "입니다. (15분 이내 인증하시길 바랍니다.)";
            case FIND_PASSWORD -> "[VYBZ] 비밀번호 찾기 인증번호 " + certificationCode + "입니다. (15분 이내 인증하시길 바랍니다.)";
            default -> "[VYBZ] 회원가입 인증번호 " + certificationCode + "입니다. (15분 이내 인증하시길 바랍니다.)";
        };

        smsCertificationUtil.sendSMS(phoneNumber, textMessage);

        redisUtil.save("sms:" + requestSmsDto.getPhoneNumber(), certificationCode, 15, TimeUnit.MINUTES);
        redisUtil.save(VerificationKeyManager.failSmsKey(phoneNumber), "0", 5L, TimeUnit.MINUTES);

    }

    @Override
    public void verifySmsCode(RequestVerificationSmsDto requestVerificationSmsDto) {

        final String number = requestVerificationSmsDto.getPhoneNumber();
        final String redisCode = redisUtil.get("sms:" + number);

        if (redisCode == null) {
            throw new BaseException(BaseResponseStatus.EXPIRED_SMS_CODE);
        }

        if (!redisCode.equals(requestVerificationSmsDto.getVerificationSmsCode())) {
            String smsFailKey = VerificationKeyManager.failSmsKey(number);

            if (redisUtil.increase(smsFailKey, 5L, TimeUnit.MINUTES) >= 10) {
                redisUtil.delete("sms:" + number);
                redisUtil.delete(smsFailKey);
                throw new BaseException(BaseResponseStatus.SMS_CODE_VERIFICATION_LIMITED);
            }
            throw new BaseException(BaseResponseStatus.INVALID_SMS_CODE);
        }

        redisUtil.save(VerificationKeyManager.smsKey(requestVerificationSmsDto.getSendPurpose(), number), "true", 10, TimeUnit.MINUTES);
        redisUtil.delete("sms:" + number);
        redisUtil.delete(VerificationKeyManager.failSmsKey(number));
    }

    private String createCertificationCode() {
        return String.valueOf((int)((Math.random() * 900000) + 100000));
    }
}
