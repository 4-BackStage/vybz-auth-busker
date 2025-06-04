package back.vybz.auth_busker.busker.vo.response;

import back.vybz.auth_busker.busker.dto.response.ResponseBuskerSignInDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseBuskerSignInVo {

    private String accessToken;

    private String refreshToken;

    private String buskerUuid;

    @Builder
    public ResponseBuskerSignInVo(String accessToken, String refreshToken,
                                  String buskerUuid) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.buskerUuid = buskerUuid;
    }

    public static ResponseBuskerSignInVo from(ResponseBuskerSignInDto responseBuskerSignInDto) {
        return ResponseBuskerSignInVo.builder()
                .accessToken(responseBuskerSignInDto.getAccessToken())
                .refreshToken(responseBuskerSignInDto.getRefreshToken())
                .buskerUuid(responseBuskerSignInDto.getBuskerUuid())
                .build();
    }
}
