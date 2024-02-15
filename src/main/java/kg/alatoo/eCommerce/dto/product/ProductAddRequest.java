package kg.alatoo.eCommerce.dto.product;

import jakarta.persistence.ManyToOne;
import kg.alatoo.eCommerce.entity.Category;
import kg.alatoo.eCommerce.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAddRequest {
    private String title;
    private Integer price;
    private String code;
    private String description;
    private User user;
    private String category;
}
