package back.vybz.auth_busker.dto.request;

import back.vybz.auth_busker.dto.SendPurpose;
import back.vybz.auth_busker.vo.request.RequestVerificationEmailVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestVerificationEmailDto {

    private String email;

    private String verificationCode;

    private SendPurpose purpose;

    @Builder
    public RequestVerificationEmailDto(String email, String verificationCode, SendPurpose purpose) {
        this.email = email;
        this.verificationCode = verificationCode;
        this.purpose = purpose;
    }

    public static RequestVerificationEmailDto from(RequestVerificationEmailVo requestVerificationEmailVo) {
        return RequestVerificationEmailDto.builder()
                .email(requestVerificationEmailVo.getEmail())
                .verificationCode(requestVerificationEmailVo.getVerificationCode())
                .purpose(SendPurpose.valueOf(requestVerificationEmailVo.getPurpose().toUpperCase()))
                .build();
    }
}
