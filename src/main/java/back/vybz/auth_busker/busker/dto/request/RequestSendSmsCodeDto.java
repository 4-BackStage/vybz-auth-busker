package back.vybz.auth_busker.busker.dto.request;

import back.vybz.auth_busker.busker.dto.SendPurpose;
import back.vybz.auth_busker.busker.vo.request.RequestSendSmsCodeVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestSendSmsCodeDto {

    private String phoneNumber;

    private SendPurpose sendPurpose;

    @Builder
    public RequestSendSmsCodeDto(String phoneNumber, SendPurpose sendPurpose) {
        this.phoneNumber = phoneNumber;
        this.sendPurpose = sendPurpose;
    }

    public static RequestSendSmsCodeDto from(RequestSendSmsCodeVo requestSmsVo) {
        return RequestSendSmsCodeDto.builder()
                .phoneNumber(requestSmsVo.getPhoneNumber())
                .sendPurpose(SendPurpose.valueOf(requestSmsVo.getPurpose().toUpperCase()))
                .build();
    }
}
