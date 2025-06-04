package back.vybz.auth_busker.busker.vo.request;

import back.vybz.auth_busker.busker.common.pattern.RegexPatterns;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestVerificationSmsVo {

    @NotBlank(message = "번호를 입력해주세요.")
    @Pattern(
            regexp = RegexPatterns.PHONE_NUMBER,
            message = "전화번호는 010-xxxx-xxxx 형식이어야 합니다."
    )
    private String phoneNumber;

    @NotBlank
    @Size(min=6, max=6, message = "인증번호는 6글자 입니다.")
    private String verificationSmsCode;

    @NotBlank(message = "비밀번호 확인을 위한 목적을 입력해주세요.")
    private String purpose;
}
