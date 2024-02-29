package kg.alatoo.eCommerce.service;

import kg.alatoo.eCommerce.dto.user.ChangePasswordRequest;
import kg.alatoo.eCommerce.dto.user.CustomerInfoResponse;
import kg.alatoo.eCommerce.dto.user.WorkerInfoResponse;

public interface UserService {
    CustomerInfoResponse customerInfo(String token);

    void update(String token, CustomerInfoResponse request);

    void changePassword(String token, ChangePasswordRequest request);

    WorkerInfoResponse workerInfo(String token);

    void addFavorite(String token, Long productId);
}
