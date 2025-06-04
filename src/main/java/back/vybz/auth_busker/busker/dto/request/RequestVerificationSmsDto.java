package back.vybz.auth_busker.busker.dto.request;

import back.vybz.auth_busker.busker.dto.SendPurpose;
import back.vybz.auth_busker.busker.vo.request.RequestVerificationSmsVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestVerificationSmsDto {

    private String phoneNumber;

    private String verificationSmsCode;

    private SendPurpose sendPurpose;

    @Builder
    public RequestVerificationSmsDto(String phoneNumber, String verificationSmsCode
    , SendPurpose sendPurpose) {
        this.phoneNumber = phoneNumber;
        this.verificationSmsCode = verificationSmsCode;
        this.sendPurpose = sendPurpose;
    }

    public static RequestVerificationSmsDto from(RequestVerificationSmsVo requestVerificationSmsVo) {
        return RequestVerificationSmsDto.builder()
                .phoneNumber(requestVerificationSmsVo.getPhoneNumber())
                .verificationSmsCode(requestVerificationSmsVo.getVerificationSmsCode())
                .sendPurpose(SendPurpose.valueOf(requestVerificationSmsVo.getPurpose().toUpperCase()))
                .build();
    }
}
