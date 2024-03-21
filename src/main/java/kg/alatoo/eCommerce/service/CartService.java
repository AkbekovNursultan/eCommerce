package kg.alatoo.eCommerce.service;

import kg.alatoo.eCommerce.dto.cart.CartInfoResponse;
import kg.alatoo.eCommerce.dto.cart.OrderHistoryResponse;

public interface CartService {
    void add(String token, Long productId, Integer quantity);

    void delete(String token, Long productId);

    CartInfoResponse info(String token);

    void buy(String token);

    OrderHistoryResponse history(String token);
}
