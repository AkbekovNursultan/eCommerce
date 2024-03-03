package kg.alatoo.eCommerce.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartElementResponse {
    private Long id;
    private String title;
    private Integer quantity;
    private Integer price;
    private Integer total;
}
