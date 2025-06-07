package back.vybz.auth_busker.kafka.event;

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

    @Builder
    public BuskerAuthEvent(String buskerUuid, List<String> categoryId, String nickname) {
        this.buskerUuid = buskerUuid;
        this.categoryId = categoryId;
        this.nickname = nickname;
    }
}

