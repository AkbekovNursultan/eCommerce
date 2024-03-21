package kg.alatoo.eCommerce.mapper;

import kg.alatoo.eCommerce.dto.cart.OrderHistoryResponse;
import kg.alatoo.eCommerce.entity.Cart;

public interface OrderHistoryMapper {
    OrderHistoryResponse toDto(Cart cart);
}
