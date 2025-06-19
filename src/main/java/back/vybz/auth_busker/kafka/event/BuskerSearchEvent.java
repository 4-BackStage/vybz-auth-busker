package back.vybz.auth_busker.kafka.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BuskerSearchEvent {

    private String buskerUuid;
    private String nickname;
    private String profileImageUrl;

    @Builder
    public BuskerSearchEvent(String buskerUuid,
                             String nickname,
                             String profileImageUrl) {
        this.buskerUuid = buskerUuid;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
