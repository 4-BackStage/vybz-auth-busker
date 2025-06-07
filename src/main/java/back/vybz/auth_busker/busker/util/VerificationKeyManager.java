package back.vybz.auth_busker.busker.util;

import back.vybz.auth_busker.busker.dto.SendPurpose;
import back.vybz.auth_busker.common.entity.BaseResponseStatus;
import back.vybz.auth_busker.common.exception.BaseException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VerificationKeyManager {

    /**
     * Redis key - email 생성
     *
     * @param purpose
     * @param email
     * @return
     */
    public static String emailKey(SendPurpose purpose, String email) {
        return switch (purpose) {
            case SIGN_UP -> "sign-up-Verified:" + email;
            case FIND_PASSWORD -> "find-password-Verified:" + email;
            default -> throw new BaseException(BaseResponseStatus.INVALID_EMAIL_REQUEST);
        };
    }

    /**
     * Redis key - sms 생성
     *
     * @param purpose
     * @param phone
     * @return
     */
    public static String smsKey(SendPurpose purpose, String phone) {
        return switch (purpose) {
            case SIGN_UP -> "sign-up-sms-Verified:" + phone;
            case FIND_EMAIL -> "find-email-sms-Verified:" + phone;
            default -> throw new BaseException(BaseResponseStatus.INVALID_SMS_REQUEST);
        };
    }

    public static String failEmailKey(String email) {
        return "EmailVerify:" + email;
    }

    public static String failSmsKey(String phone) {
        return "SMSVerify:" + phone;
    }
}
