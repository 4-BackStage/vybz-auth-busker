package back.vybz.auth_busker.vo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestSendSmsCodeVo {

    @NotEmpty(message = "휴대폰 번호를 입력해주세요")
    private String phoneNumber;

    @NotBlank
    private String purpose;
}
