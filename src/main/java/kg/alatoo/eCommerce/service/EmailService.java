package kg.alatoo.eCommerce.service;
import kg.alatoo.eCommerce.dto.email.EmailRequest;
import kg.alatoo.eCommerce.dto.email.RecoveryRequest;
import kg.alatoo.eCommerce.dto.email.VerificationRequest;

public interface EmailService {
    String verifyEmail(VerificationRequest request, String token);

    void sendVerificationCode(EmailRequest request, String token);

    String recovery(String email);

    String recoverPassword(String code, RecoveryRequest request);
}
