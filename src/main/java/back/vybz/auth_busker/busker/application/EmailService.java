package back.vybz.auth_busker.busker.application;


import back.vybz.auth_busker.busker.dto.request.RequestSendEmailCodeDto;
import back.vybz.auth_busker.busker.dto.request.RequestVerificationEmailDto;

public interface EmailService {

    void sendEmailCode(RequestSendEmailCodeDto requestSendEmailCodeDto);

    void verifyEmailCode(RequestVerificationEmailDto requestVerificationEmailDto);
}
