package back.vybz.auth_busker.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseBuskerSignInDto {

    private String accessToken;

    private String refreshToken;

    private String buskerUuid;

    @Builder
    public ResponseBuskerSignInDto(String accessToken, String refreshToken, String buskerUuid) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.buskerUuid = buskerUuid;
    }
}
