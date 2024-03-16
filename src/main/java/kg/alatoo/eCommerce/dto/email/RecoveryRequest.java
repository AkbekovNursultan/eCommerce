package kg.alatoo.eCommerce.dto.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecoveryRequest {
    private String newPassword;
    private String confirmPassword;
}
