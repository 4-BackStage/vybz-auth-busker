package back.vybz.auth_busker.busker.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestAgreementConsentDto {

    private Long agreementId;

    private Boolean agreed;

    @Builder
    public RequestAgreementConsentDto(Long agreementId, Boolean agreed) {
        this.agreementId = agreementId;
        this.agreed = agreed;
    }
}
