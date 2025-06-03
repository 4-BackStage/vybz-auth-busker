package back.vybz.auth_busker.infrastructure;

import back.vybz.auth_busker.domain.Busker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Busker, Long> {

    Optional<Busker> findByBuskerUuid(String buskerUUid);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Busker> findByEmail(String email);
}
