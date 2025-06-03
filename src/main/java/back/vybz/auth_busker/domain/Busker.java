package back.vybz.auth_busker.domain;

import back.vybz.auth_busker.common.entity.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "busker")
@Getter
@NoArgsConstructor
public class Busker extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 버스커 uuid
     */
    @Column(name = "busker_uuid", nullable = false, unique = true)
    private String buskerUuid;

    /**
     * 이메일
     */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * 전화번호
     */
    @Column(name = "number", unique = true, nullable = false)
    private String phoneNumber;

    /**
     * 패스워드
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * 사용자 상태
     */
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Busker(Long id, String buskerUuid, String email, String phoneNumber,
                  String password, Status status) {
        this.id = id;
        this.buskerUuid = buskerUuid;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.status = status;
    }
}
