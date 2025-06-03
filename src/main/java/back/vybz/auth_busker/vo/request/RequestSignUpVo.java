package back.vybz.auth_busker.vo.request;

import back.vybz.auth_busker.common.pattern.RegexPatterns;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestSignUpVo {

    @NotBlank
    @Pattern(
            regexp = RegexPatterns.EMAIL,
            message = "이메일은 10자 이상 30자 이하로 입력해주세요"
    )
    private String email;


    @NotBlank
    @Pattern(
            regexp =  RegexPatterns.PASSWORD,
            message = "비밀번호는 영문자, 숫자, 특수문자를 포함하여 8~20자여야 합니다."
    )
    private String password;

    @NotBlank(message = "카테고리를 선택해주세요.")
    private String categoryId;

    @NotBlank
    @Pattern(
            regexp = RegexPatterns.PHONE_NUMBER,
            message = "전화번호는 010-xxxx-xxxx 형식이어야 합니다."
    )
    private String phoneNumber;

    @NotBlank
    @Pattern(
            regexp = RegexPatterns.NICKNAME,
            message = "닉네임은 1자 이상 20자 이하로 입력해주세요"
    )
    private String nickname;
}
