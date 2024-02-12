package kg.alatoo.eCommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponse {
    private Long id;
    private String username;
    private String token;
}
