package back.vybz.auth_busker.busker.vo.request;

import back.vybz.auth_busker.busker.common.pattern.RegexPatterns;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestExistsEmailVo {

    @NotBlank
    @Pattern(
            regexp = RegexPatterns.EMAIL,
            message = "이메일은 10자 이상 30자 이하로 입력해주세요"
    )
    private String email;
}
