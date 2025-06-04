package back.vybz.auth_busker.busker.dto.request;

import back.vybz.auth_busker.busker.domain.Busker;
import back.vybz.auth_busker.busker.domain.Status;
import back.vybz.auth_busker.busker.vo.request.RequestSignUpVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class RequestSignUpDto {

    private String email;

    private String password;

    private String categoryId;

    private String phoneNumber;

    private String nickname;

    @Builder
    public RequestSignUpDto(String email, String password,
                            String categoryId, String phoneNumber, String nickname) {
        this.email = email;
        this.password = password;
        this.categoryId = categoryId;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
    }

    public Busker toEntity(PasswordEncoder passwordEncoder) {
        return Busker.builder()
                .buskerUuid(UUID.randomUUID().toString())
                .email(email)
                .password(passwordEncoder.encode(password))
                .phoneNumber(phoneNumber.replaceAll("-", ""))
                .status(Status.ACTIVE)
                .build();
    }

    public static RequestSignUpDto from(RequestSignUpVo requestSignUpVo) {
        return RequestSignUpDto.builder()
                .email(requestSignUpVo.getEmail())
                .password(requestSignUpVo.getPassword())
                .phoneNumber(requestSignUpVo.getPhoneNumber())
                .nickname(requestSignUpVo.getNickname())
                .categoryId(requestSignUpVo.getCategoryId())
                .build();
    }
}
