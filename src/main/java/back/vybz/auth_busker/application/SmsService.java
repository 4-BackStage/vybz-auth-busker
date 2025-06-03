package back.vybz.auth_busker.application;

import back.vybz.auth_busker.dto.request.RequestSendSmsCodeDto;
import back.vybz.auth_busker.dto.request.RequestVerificationSmsDto;

public interface SmsService {

    void sendSms(RequestSendSmsCodeDto requestSmsDto);

    void verifySmsCode(RequestVerificationSmsDto requestVerificationSmsDto);
}
