package back.vybz.auth_busker.busker.dto.request;

import back.vybz.auth_busker.busker.dto.SendPurpose;
import back.vybz.auth_busker.busker.vo.request.RequestVerificationEmailVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestVerificationEmailDto {

    private String email;

    private String verificationCode;

    private SendPurpose sendPurpose;

    @Builder
    public RequestVerificationEmailDto(String email, String verificationCode, SendPurpose sendPurpose) {
        this.email = email;
        this.verificationCode = verificationCode;
        this.sendPurpose = sendPurpose;
    }

    public static RequestVerificationEmailDto from(RequestVerificationEmailVo requestVerificationEmailVo) {
        return RequestVerificationEmailDto.builder()
                .email(requestVerificationEmailVo.getEmail())
                .verificationCode(requestVerificationEmailVo.getVerificationCode())
                .sendPurpose(SendPurpose.valueOf(requestVerificationEmailVo.getPurpose().toUpperCase()))
                .build();
    }
}
