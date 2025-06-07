package back.vybz.auth_busker.busker.dto.request;

import back.vybz.auth_busker.busker.domain.Busker;
import back.vybz.auth_busker.busker.domain.Status;
import back.vybz.auth_busker.busker.vo.request.RequestAgreementConsentVo;
import back.vybz.auth_busker.busker.vo.request.RequestSignUpVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class RequestSignUpDto {

    private String email;

    private String password;

    private List<Long> categoryId;

    private String phoneNumber;

    private String nickname;

    private List<RequestAgreementConsentDto> agreements;

    private String profileImageUrl;

    private String introduction;

    @Builder
    public RequestSignUpDto(String email, String password, List<Long> categoryId, String phoneNumber,
                            String nickname, List<RequestAgreementConsentDto> agreements,
                            String profileImageUrl, String introduction) {
        this.email = email;
        this.password = password;
        this.categoryId = categoryId;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.agreements = agreements;
        this.profileImageUrl = profileImageUrl;
        this.introduction = introduction;
    }

    public Busker toEntity(PasswordEncoder passwordEncoder) {
        return Busker.builder()
                .buskerUuid(UUID.randomUUID().toString())
                .email(email)
                .password(passwordEncoder.encode(password))
                .phoneNumber(phoneNumber.replaceAll("-", ""))
                .status(Status.ACTIVE)
                .build();
    }

    public static RequestSignUpDto from(RequestSignUpVo requestSignUpVo) {
        return RequestSignUpDto.builder()
                .email(requestSignUpVo.getEmail())
                .password(requestSignUpVo.getPassword())
                .phoneNumber(requestSignUpVo.getPhoneNumber())
                .nickname(requestSignUpVo.getNickname())
                .categoryId(requestSignUpVo.getCategoryId())
                .agreements(mapAgreements(requestSignUpVo.getAgreements()))
                .profileImageUrl(requestSignUpVo.getProfileImageUrl())
                .introduction(requestSignUpVo.getIntroduction())
                .build();
    }

    private static List<RequestAgreementConsentDto> mapAgreements(List<RequestAgreementConsentVo> requestAgreements) {
        if (requestAgreements == null || requestAgreements.isEmpty()) return List.of();
        return requestAgreements.stream()
                .map(vo -> RequestAgreementConsentDto.builder()
                        .agreementId(vo.getAgreementId())
                        .agreed(vo.getAgreed())
                        .build())
                .toList();
    }

}
