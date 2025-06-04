package back.vybz.auth_busker.busker.presentation;

import back.vybz.auth_busker.busker.application.AuthService;
import back.vybz.auth_busker.busker.dto.request.RequestAuthSignInDto;
import back.vybz.auth_busker.busker.dto.request.RequestExistsEmailDto;
import back.vybz.auth_busker.busker.dto.request.RequestSignUpDto;
import back.vybz.auth_busker.busker.vo.request.RequestAuthSignInVo;
import back.vybz.auth_busker.busker.vo.request.RequestExistsEmailVo;
import back.vybz.auth_busker.busker.vo.request.RequestSignUpVo;
import back.vybz.auth_busker.busker.common.application.ReissueService;
import back.vybz.auth_busker.busker.dto.response.ResponseBuskerSignInDto;
import back.vybz.auth_busker.busker.common.entity.BaseResponseEntity;
import back.vybz.auth_busker.busker.common.entity.BaseResponseStatus;
import back.vybz.auth_busker.busker.vo.response.ResponseBuskerSignInVo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/busker")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final ReissueService reissueService;

    @Operation(summary = "Busker Sign-Up API", description = "버스커 회원가입", tags = {"Auth-service"})
    @PostMapping("/sign-up")
    public BaseResponseEntity<Void> signUp(
            @Valid @RequestBody RequestSignUpVo requestSignUpVo
    ) {
        authService.signUp(RequestSignUpDto.from(requestSignUpVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SIGN_UP_SUCCESS);
    }

    @Operation(summary = "Busker Check Email API", description = "이메일 중복 확인", tags = {"Auth-service"})
    @PostMapping("/check/email")
    public BaseResponseEntity<Boolean> emailExists(
            @Valid @RequestBody RequestExistsEmailVo requestExistsEmailVo
    ) {
        return new BaseResponseEntity<>(
                authService.existsEmail(RequestExistsEmailDto.from(requestExistsEmailVo).getEmail())
        );
    }

    @Operation(summary = "Busker SignIn API", description = "버스커 로그인", tags = {"Auth-service"})
    @PostMapping("/sign-in")
    public BaseResponseEntity<ResponseBuskerSignInVo> signIn(
            @Valid @RequestBody RequestAuthSignInVo requestAuthSignInVo
    ) {

        ResponseBuskerSignInDto responseBuskerSignInDto = authService.signIn(RequestAuthSignInDto.from(requestAuthSignInVo));

        return new BaseResponseEntity<>(
                BaseResponseStatus.SIGN_IN_SUCCESS, ResponseBuskerSignInVo.from(responseBuskerSignInDto)
        );
    }

    @Operation(
            summary = "Busker 액세스 토큰 재발급 API",
            description = "Busker 리프레시 토큰을 이용해 액세스 토큰을 재발급하는 API 입니다.",
            tags = {"Auth-service"}
    )
    @PostMapping("/reissue")
    public BaseResponseEntity<ResponseBuskerSignInVo> buskerReissue(
            @RequestHeader("Authorization") String authorization
    ) {

        ResponseBuskerSignInDto resultDto = reissueService.buskerReissue(authorization);

        return new BaseResponseEntity<>(ResponseBuskerSignInVo.from(resultDto));
    }

    @Operation(
            summary = "Busker SignOut API",
            description = "로그아웃을 위한 API 입니다.",
            tags = {"Auth-service"}
    )
    @PostMapping("/sign-out")
    public BaseResponseEntity<Void> buskerLogout(
            @RequestHeader("Authorization") String authorization
    ) {
        authService.signOut(authorization);

        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
