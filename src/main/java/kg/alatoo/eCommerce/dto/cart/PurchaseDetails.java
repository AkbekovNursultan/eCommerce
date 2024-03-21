package kg.alatoo.eCommerce.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseDetails {
    private Long id;
    private Integer cost;
    private String date;
}
