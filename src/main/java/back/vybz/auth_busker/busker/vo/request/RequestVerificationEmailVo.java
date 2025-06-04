package back.vybz.auth_busker.busker.vo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestVerificationEmailVo {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank
    @Size(min=6, max=6, message = "인증번호는 6글자 입니다.")
    private String verificationCode;

    @NotBlank(message = "비밀번호 확인을 위한 목적을 입력해주세요.")
    private String purpose;
}
