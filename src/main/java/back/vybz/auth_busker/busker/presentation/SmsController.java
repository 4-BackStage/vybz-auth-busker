package back.vybz.auth_busker.busker.presentation;

import back.vybz.auth_busker.busker.application.SmsService;
import back.vybz.auth_busker.busker.dto.request.RequestSendSmsCodeDto;
import back.vybz.auth_busker.busker.dto.request.RequestVerificationSmsDto;
import back.vybz.auth_busker.busker.vo.request.RequestSendSmsCodeVo;
import back.vybz.auth_busker.busker.vo.request.RequestVerificationSmsVo;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/busker")
public class SmsController {

    private final SmsService smsService;

    @Operation(summary = "Send SMS Code API", description = "전화번호 인증 코드 발송", tags = {"SMS-service"})
    @PostMapping("/sms-code")
    public BaseResponseEntity<Void> sendSMS(
            @Valid @RequestBody RequestSendSmsCodeVo requestSendSmsCodeVo
    ) {
        smsService.sendSms(RequestSendSmsCodeDto.from(requestSendSmsCodeVo));

        return new BaseResponseEntity<>(BaseResponseStatus.SMS_CODE_SUCCESS);
    }

    @Operation(summary = "Verify SMS Code API", description = "전화번호 인증 코드 검증", tags = {"SMS-service"})
    @PostMapping("/sms-verify")
    public BaseResponseEntity<Void> verifySMS(
            @Valid @RequestBody RequestVerificationSmsVo requestVerificationSmsVo
    ) {
        smsService.verifySmsCode(RequestVerificationSmsDto.from(requestVerificationSmsVo));

        return new BaseResponseEntity<>(BaseResponseStatus.SMS_CODE_VERIFICATION_SUCCESS);
    }
}
