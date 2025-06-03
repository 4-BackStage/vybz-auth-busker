package back.vybz.auth_busker.util;

import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmsCertificationUtil {

    private final DefaultMessageService defaultMessageService;

    @Value("${cools.api.sender-number}")
    private String senderNumber;

    public void sendSMS(String to,  String fullMessage){
        Message message = new Message();
        message.setFrom(senderNumber);
        message.setTo(to);
        message.setText(fullMessage);

        defaultMessageService.sendOne(new SingleMessageSendingRequest(message));
    }
}
