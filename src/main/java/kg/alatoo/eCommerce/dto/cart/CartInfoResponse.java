package kg.alatoo.eCommerce.dto.cart;

import kg.alatoo.eCommerce.entity.CartElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartInfoResponse {
    private Long id;
    private List<CartElementResponse> list;
    private Integer totalPrice;
}
