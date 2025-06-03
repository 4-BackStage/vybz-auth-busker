package back.vybz.auth_busker.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomBuskerDetails implements UserDetails {

    private final Busker busker;

    public CustomBuskerDetails(Busker busker) {
        this.busker = busker;
    }

    @Override
    public String getUsername() {
        return busker.getBuskerUuid();
    }

    @Override
    public String getPassword() {
        return busker.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return busker.getStatus().name().equals("ACTIVE");
    }
}
