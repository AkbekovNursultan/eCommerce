package kg.alatoo.eCommerce.mapper;

import kg.alatoo.eCommerce.dto.cart.CartInfoResponse;
import kg.alatoo.eCommerce.entity.Cart;

public interface CartMapper {
    CartInfoResponse toDto(Cart cart);
}
