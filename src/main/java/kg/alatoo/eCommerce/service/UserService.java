package kg.alatoo.eCommerce.service;

import kg.alatoo.eCommerce.dto.user.ChangePasswordRequest;
import kg.alatoo.eCommerce.dto.user.UserInfoResponse;

public interface UserService {
    UserInfoResponse userInfo(String token);

    void update(String token, UserInfoResponse request);

    void changePassword(String token, ChangePasswordRequest request);
}
