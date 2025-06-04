package back.vybz.auth_busker.busker.dto.request;

import back.vybz.auth_busker.busker.vo.request.RequestExistsEmailVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestExistsEmailDto {

    private String email;

    @Builder
    public RequestExistsEmailDto(String email) {
        this.email = email;
    }

    public static RequestExistsEmailDto from(RequestExistsEmailVo requestExistsEmailVo) {
        return RequestExistsEmailDto.builder()
                .email(requestExistsEmailVo.getEmail())
                .build();
    }
}
