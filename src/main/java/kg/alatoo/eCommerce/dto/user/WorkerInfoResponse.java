package kg.alatoo.eCommerce.dto.user;

import kg.alatoo.eCommerce.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkerInfoResponse {
    private Long id;
    private String username;
    private String email;

    private String firstName;
    private String lastName;
    private List<Product> addedProductsList;

}
