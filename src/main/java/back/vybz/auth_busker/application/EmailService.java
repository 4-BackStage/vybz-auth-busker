package back.vybz.auth_busker.application;


import back.vybz.auth_busker.dto.request.RequestSendEmailCodeDto;
import back.vybz.auth_busker.dto.request.RequestVerificationEmailDto;

public interface EmailService {

    void sendEmailCode(RequestSendEmailCodeDto requestSendEmailCodeDto);

    void verifyEmailCode(RequestVerificationEmailDto requestVerificationEmailDto);
}
