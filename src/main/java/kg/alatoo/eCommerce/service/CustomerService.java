package kg.alatoo.eCommerce.service;

import kg.alatoo.eCommerce.dto.product.ProductDetailsResponse;
import kg.alatoo.eCommerce.dto.product.ProductResponse;
import kg.alatoo.eCommerce.dto.user.ChangePasswordRequest;
import kg.alatoo.eCommerce.dto.user.CustomerInfoResponse;

import java.util.List;

public interface CustomerService {
    CustomerInfoResponse customerInfo(String token);

    void update(String token, CustomerInfoResponse request);

    void changePassword(String token, ChangePasswordRequest request);

    void addFavorite(String token, Long productId);

    void deleteFavorite(String token, Long productId);

    List<ProductResponse> getFavorites(String token);
}
