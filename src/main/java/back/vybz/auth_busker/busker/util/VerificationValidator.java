package back.vybz.auth_busker.busker.util;

import back.vybz.auth_busker.busker.dto.SendPurpose;
import back.vybz.auth_busker.common.entity.BaseResponseStatus;
import back.vybz.auth_busker.common.exception.BaseException;
import back.vybz.auth_busker.common.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VerificationValidator {

    private final RedisUtil<String> redisUtil;

    public void validate(SendPurpose purpose, String email, String phone) {
        switch (purpose) {
            case SIGN_UP -> {
                checkVerified(VerificationKeyManager.emailKey(purpose, email), BaseResponseStatus.SIGN_UP_NOT_VERIFIED);
                checkVerified(VerificationKeyManager.smsKey(purpose, phone), BaseResponseStatus.SIGN_UP_NOT_SMS_VERIFIED);
            }
            case FIND_PASSWORD -> {
                checkVerified(VerificationKeyManager.emailKey(purpose, email), BaseResponseStatus.SIGN_UP_NOT_VERIFIED);

            }
            case FIND_EMAIL -> {
                checkVerified(VerificationKeyManager.smsKey(purpose, phone), BaseResponseStatus.SMS_VERIFICATION_REQUIRED);
            }
        }
    }

    private void checkVerified(String key, BaseResponseStatus errorStatus) {
        if (!"true".equals(redisUtil.get(key))) {
            throw new BaseException(errorStatus);
        }
    }

}
