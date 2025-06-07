package back.vybz.auth_busker.busker.vo.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestAgreementConsentVo {

    private Long agreementId;

    private Boolean agreed;
}
