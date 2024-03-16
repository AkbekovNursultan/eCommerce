package kg.alatoo.eCommerce.dto.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificationRequest {
    private String verificationCode;
}
