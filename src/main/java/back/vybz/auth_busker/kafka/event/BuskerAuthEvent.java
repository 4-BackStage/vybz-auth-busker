package back.vybz.auth_busker.kafka.event;

import back.vybz.auth_busker.busker.dto.request.RequestAgreementConsentDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BuskerAuthEvent {

    private String buskerUuid;

    private List<String> categoryId;

    private String nickname;

    private List<RequestAgreementConsentDto> agreements;

    private String profileImageUrl;

    private String introduction;

    @Builder
    public BuskerAuthEvent(String buskerUuid, List<String> categoryId, String nickname,
                           List<RequestAgreementConsentDto> agreements, String profileImageUrl, String introduction) {
        this.buskerUuid = buskerUuid;
        this.categoryId = categoryId;
        this.nickname = nickname;
        this.agreements = agreements;
        this.profileImageUrl = profileImageUrl;
        this.introduction = introduction;
    }
}

