package kg.alatoo.eCommerce.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String title;
    private Integer price;
    private String category;
    private Integer quantity;
}
