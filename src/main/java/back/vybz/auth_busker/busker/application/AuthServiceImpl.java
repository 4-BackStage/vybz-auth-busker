package back.vybz.auth_busker.busker.application;

import back.vybz.auth_busker.busker.common.application.TokenService;
import back.vybz.auth_busker.busker.common.entity.BaseResponseStatus;
import back.vybz.auth_busker.busker.common.exception.BaseException;
import back.vybz.auth_busker.busker.common.jwt.JwtProvider;
import back.vybz.auth_busker.busker.common.util.RedisUtil;
import back.vybz.auth_busker.busker.domain.Busker;
import back.vybz.auth_busker.busker.domain.CustomBuskerDetails;
import back.vybz.auth_busker.busker.domain.Status;
import back.vybz.auth_busker.busker.dto.request.RequestAuthSignInDto;
import back.vybz.auth_busker.busker.dto.request.RequestSignUpDto;
import back.vybz.auth_busker.busker.dto.response.ResponseBuskerSignInDto;
import back.vybz.auth_busker.busker.infrastructure.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;

    private final PasswordEncoder passwordEncoder;

    private final RedisUtil<String> redisUtil;

    private final TokenService tokenService;

    private final JwtProvider jwtProvider;

    @Override
    public UserDetails loadBuskerByUuid(String buskerUuid) {
        return authRepository.findByBuskerUuid(buskerUuid)
                .map(CustomBuskerDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("해당 UUID 버스커를 찾을 수 없습니다."));
    }

    @Transactional
    @Override
    public void signUp(RequestSignUpDto requestSignUpDto) {

        String email = requestSignUpDto.getEmail();
        String phone = requestSignUpDto.getPhoneNumber();

        if(authRepository.existsByEmail(email)) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_EMAIL);
        }

        if (authRepository.existsByPhoneNumber(phone)) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_SMS);
        }

        String emailVerifyKey = "sign-up-Verified:" + email;

        String smsVerifyKey = "sign-up-sms-Verified:" + phone;

        if (!"true".equals(redisUtil.get(emailVerifyKey))) {
            throw new BaseException(BaseResponseStatus.SIGN_UP_NOT_VERIFIED);
        }

        if (!"true".equals(redisUtil.get(smsVerifyKey))) {
            throw new BaseException(BaseResponseStatus.SIGN_UP_NOT_SMS_VERIFIED);
        }

        authRepository.save(requestSignUpDto.toEntity(passwordEncoder));

        redisUtil.delete(emailVerifyKey);
        redisUtil.delete(smsVerifyKey);
    }

    @Override
    public ResponseBuskerSignInDto signIn(RequestAuthSignInDto requestAuthSignInDto) {

        Busker busker = authRepository.findByEmail(requestAuthSignInDto.getEmail())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_LOGIN));

        if (!passwordEncoder.matches(requestAuthSignInDto.getPassword(), busker.getPassword())) {
            throw new BaseException(BaseResponseStatus.INVALID_LOGIN);
        } else if (busker.getStatus() == Status.WITHDRAWAL) {
            throw new BaseException(BaseResponseStatus.WITHDRAWAL_PENDING);
        }

        return tokenService.issueBuskerToken(busker);
    }

    @Transactional
    @Override
    public void signOut(String refreshToken) {

        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            throw new BaseException(BaseResponseStatus.INVALID_REFRESH_TOKEN);
        }

        refreshToken = refreshToken.replace("Bearer ", "");

        String uuid = jwtProvider.extractSubject(refreshToken);

        redisUtil.delete("Refresh:" + uuid);
    }
}
