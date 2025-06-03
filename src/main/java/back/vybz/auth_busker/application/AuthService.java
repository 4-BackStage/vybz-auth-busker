package back.vybz.auth_busker.application;

import back.vybz.auth_busker.dto.request.RequestAuthSignInDto;
import back.vybz.auth_busker.dto.request.RequestSignUpDto;
import back.vybz.auth_busker.dto.response.ResponseBuskerSignInDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

    UserDetails loadBuskerByUuid(String buskerUuid);

    void signUp(RequestSignUpDto requestSignUpDto);

    boolean existsEmail(String email);

    ResponseBuskerSignInDto signIn(RequestAuthSignInDto requestAuthSignInDto);

    void signOut(String refreshToken);
}
