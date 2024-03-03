package kg.alatoo.eCommerce.dto.user;

import jakarta.persistence.OneToMany;
import kg.alatoo.eCommerce.entity.CartElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerInfoResponse {
    private Long id;
    private String username;
    private String email;

    private String firstName;
    private String lastName;
    private String country;
    private String address;
    private String city;
    private String zipCode;
    private String phone;
    private String additionalInfo;

    @OneToMany
    private List<CartElement> productList;
}
