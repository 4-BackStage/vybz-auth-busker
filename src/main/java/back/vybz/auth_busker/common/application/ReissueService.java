package back.vybz.auth_busker.common.application;

import back.vybz.auth_busker.common.entity.BaseResponseStatus;
import back.vybz.auth_busker.common.exception.BaseException;
import back.vybz.auth_busker.common.jwt.JwtProvider;
import back.vybz.auth_busker.common.util.RedisUtil;
import back.vybz.auth_busker.busker.dto.response.ResponseBuskerSignInDto;
import back.vybz.auth_busker.busker.infrastructure.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final JwtProvider jwtProvider;

    private final RedisUtil<String> redisUtil;

    private final AuthRepository authRepository;

    private final TokenService tokenService;


    public ResponseBuskerSignInDto buskerReissue(String authorization) {

        String refreshToken = parseAndValidate(authorization);

        String buskerUuid = jwtProvider.extractSubject(refreshToken);

        validateRedisToken("Refresh_busker:" + buskerUuid, refreshToken);

        return authRepository.findByBuskerUuid(buskerUuid)
                .map(tokenService::issueBuskerToken)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_AUTH));
    }

    private String parseAndValidate(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new BaseException(BaseResponseStatus.INVALID_REFRESH_TOKEN);
        }

        String refreshToken = authorization.replace("Bearer ", "");

        jwtProvider.validateToken(refreshToken);

        return refreshToken;
    }

    private void validateRedisToken(String redisKey, String refreshToken) {
        String redisToken = redisUtil.get(redisKey);
        if (redisToken == null || !redisToken.equals(refreshToken)) {
            throw new BaseException(BaseResponseStatus.REFRESH_TOKEN_NOT_FOUND);
        }
    }
}
