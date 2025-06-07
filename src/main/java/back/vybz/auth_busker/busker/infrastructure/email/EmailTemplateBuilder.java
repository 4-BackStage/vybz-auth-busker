package back.vybz.auth_busker.busker.infrastructure.email;

import org.springframework.stereotype.Component;

@Component
public class EmailTemplateBuilder {
    public String buildVerificationEmail(String purpose, String code) {
        return """
            <html>
                <body style="margin:0; padding:40px; background-color:rgba(255,255,255); font-family:'Helvetica Neue', Arial, sans-serif;">
                    <div style="max-width:600px; margin:auto; background-color:#1F2937; border-radius:12px; overflow:hidden; box-shadow:0 4px 20px rgba(0,0,0,0.3); text-align:center;">
                        <div style="background-color:#38BDF8; padding:24px;">
                           <img src="https://vybz.s3.ap-northeast-2.amazonaws.com/vybz_logo.png" alt="VYBZ Logo" style="height:40px;"/>
                        </div>
                        <div style="padding:32px 24px;">
                            <h2 style="color:#38BDF8; margin-bottom:16px;">VYBZ 이메일 인증</h2>
                            <p style="font-size:16px; color:#E5E7EB;">아래 인증번호를 입력하여 %s 완료해주세요.</p>
                            <div style="margin:32px 0;">
                                <span style="display:inline-block; font-size:28px; color:#38BDF8; font-weight:bold; background-color:rgba(255,255,255); padding:12px 24px; border:2px solid #38BDF8; border-radius:8px;">
                                    %s
                                </span>
                            </div>
                            <p style="font-size:14px; color:#9CA3AF;">인증번호는 5분간 유효합니다.</p>
                        </div>
                        <div style="background-color:#38BDF8; padding:16px; font-size:12px; color:#6B7280;">
                            본 메일은 VYBZ에서 발송되었습니다.
                        </div>
                    </div>
                </body>
            </html>
        """.formatted(purpose, code);
    }
}


