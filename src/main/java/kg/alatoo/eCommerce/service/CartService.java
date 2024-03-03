package kg.alatoo.eCommerce.service;

import kg.alatoo.eCommerce.dto.cart.CartInfoResponse;

public interface CartService {
    void buy(String token, Long productId, Integer quantity);

    void delete(String token, Long productId);

    CartInfoResponse info(String token);
}
