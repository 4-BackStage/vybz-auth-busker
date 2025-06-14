package back.vybz.auth_busker.busker.application;

import back.vybz.auth_busker.busker.dto.SendPurpose;
import back.vybz.auth_busker.busker.util.VerificationValidator;
import back.vybz.auth_busker.common.application.TokenService;
import back.vybz.auth_busker.common.entity.BaseResponseStatus;
import back.vybz.auth_busker.common.exception.BaseException;
import back.vybz.auth_busker.common.jwt.JwtProvider;
import back.vybz.auth_busker.common.util.RedisUtil;
import back.vybz.auth_busker.busker.domain.Busker;
import back.vybz.auth_busker.busker.domain.CustomBuskerDetails;
import back.vybz.auth_busker.busker.domain.Status;
import back.vybz.auth_busker.busker.dto.request.RequestAuthSignInDto;
import back.vybz.auth_busker.busker.dto.request.RequestSignUpDto;
import back.vybz.auth_busker.busker.dto.response.ResponseBuskerSignInDto;
import back.vybz.auth_busker.busker.infrastructure.AuthRepository;
import back.vybz.auth_busker.kafka.event.BuskerAuthEvent;
import back.vybz.auth_busker.kafka.producer.BuskerKafkaProducer;
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

    private final VerificationValidator verificationValidator;

    private final BuskerKafkaProducer buskerKafkaProducer;

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

        verificationValidator.validate(SendPurpose.SIGN_UP, email, phone);

        Busker savedBusker = authRepository.save(requestSignUpDto.toEntity(passwordEncoder));

        redisUtil.delete("sign-up-Verified:" + email);
        redisUtil.delete("sign-up-sms-Verified:" + phone);

        buskerKafkaProducer.sendBuskerAuthEvent(BuskerAuthEvent.builder()
                .buskerUuid(savedBusker.getBuskerUuid())
                .nickname(requestSignUpDto.getNickname())
                .categoryId(requestSignUpDto.getCategoryId())
                .agreements(requestSignUpDto.getAgreements())
                .profileImageUrl(requestSignUpDto.getProfileImageUrl())
                .introduction(requestSignUpDto.getIntroduction())
                .build());
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

        redisUtil.delete("Refresh_busker:" + uuid);
    }
}
