package back.vybz.auth_busker.presentation;

import back.vybz.auth_busker.application.EmailService;
import back.vybz.auth_busker.dto.request.RequestSendEmailCodeDto;
import back.vybz.auth_busker.dto.request.RequestVerificationEmailDto;
import back.vybz.auth_busker.vo.request.RequestSendEmailCodeVo;
import back.vybz.auth_busker.vo.request.RequestVerificationEmailVo;
import back.vybz.auth_busker.common.entity.BaseResponseEntity;
import back.vybz.auth_busker.common.entity.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/busker")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @Operation(summary = "Send Email Code API", description = "이메일 인증 코드 발송", tags = {"Email-service"})
    @PostMapping("/email-code")
    public BaseResponseEntity<Void> sendEmailCode(
            @Valid @RequestBody RequestSendEmailCodeVo requestSendEmailCodeVo
    ) {
        emailService.sendEmailCode(RequestSendEmailCodeDto.from(requestSendEmailCodeVo));
        return new BaseResponseEntity<>(BaseResponseStatus.EMAIL_CODE_SUCCESS);
    }

    @Operation(summary = "Verify Email Code API", description = "이메일 인증 코드 검증", tags = {"Email-service"})
    @PostMapping("/email-verify")
    public BaseResponseEntity<Void> verifyEmailCode(
            @Valid @RequestBody RequestVerificationEmailVo requestVerificationEmailVo
    ) {
        emailService.verifyEmailCode(RequestVerificationEmailDto.from(requestVerificationEmailVo));
        return new BaseResponseEntity<>(BaseResponseStatus.EMAIL_CODE_VERIFICATION_SUCCESS);
    }
}
