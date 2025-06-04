package back.vybz.auth_busker.busker.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SendPurpose {
    SIGN_UP("sign up"),
    FIND_PASSWORD("reset password"),
    FIND_EMAIL("find email");

    private final String sendEmailPurpose;
}
