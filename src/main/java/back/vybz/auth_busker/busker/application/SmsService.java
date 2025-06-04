package back.vybz.auth_busker.busker.application;

import back.vybz.auth_busker.busker.dto.request.RequestSendSmsCodeDto;
import back.vybz.auth_busker.busker.dto.request.RequestVerificationSmsDto;

public interface SmsService {

    void sendSms(RequestSendSmsCodeDto requestSmsDto);

    void verifySmsCode(RequestVerificationSmsDto requestVerificationSmsDto);
}
