package back.vybz.auth_busker.dto.request;

import back.vybz.auth_busker.vo.request.RequestAuthSignInVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestAuthSignInDto {

    private String email;

    private String password;

    @Builder
    public RequestAuthSignInDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static RequestAuthSignInDto from(RequestAuthSignInVo requestAuthSignInVo) {
        return RequestAuthSignInDto.builder()
                .email(requestAuthSignInVo.getEmail())
                .password(requestAuthSignInVo.getPassword())
                .build();
    }
}
