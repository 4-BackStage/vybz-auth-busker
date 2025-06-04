package back.vybz.auth_busker.busker.common.application;

import back.vybz.auth_busker.busker.common.jwt.JwtProvider;
import back.vybz.auth_busker.busker.common.util.RedisUtil;
import back.vybz.auth_busker.busker.domain.Busker;
import back.vybz.auth_busker.busker.dto.response.ResponseBuskerSignInDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtProvider jwtProvider;

    private final RedisUtil<String> redisUtil;

    public ResponseBuskerSignInDto issueBuskerToken(Busker busker) {

        String accessToken = jwtProvider.createBuskerAccessToken(busker.getBuskerUuid());
        String refreshToken = jwtProvider.createBuskerRefreshToken(busker.getBuskerUuid());

        redisUtil.save("Refresh_busker:" + busker.getBuskerUuid(), refreshToken, 15, TimeUnit.DAYS);

        return ResponseBuskerSignInDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .buskerUuid(busker.getBuskerUuid())
                .build();
    }
}
