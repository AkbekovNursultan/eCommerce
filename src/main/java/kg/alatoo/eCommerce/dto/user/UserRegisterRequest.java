package kg.alatoo.eCommerce.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
    private String username;
    private String email;
    private String password;
    private String role;

    private String firstName;
    private String lastName;
    private String country;
    private String address;
    private String city;
    private String zipCode;
    private String phone;
    private String additionalInfo;
}
