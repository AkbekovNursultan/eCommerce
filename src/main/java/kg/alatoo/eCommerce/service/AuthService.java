package kg.alatoo.eCommerce.service;

import kg.alatoo.eCommerce.dto.UserLoginRequest;
import kg.alatoo.eCommerce.dto.UserLoginResponse;
import kg.alatoo.eCommerce.dto.UserRegisterRequest;
import kg.alatoo.eCommerce.entity.User;

public interface AuthService {
    void register(UserRegisterRequest userRegisterRequest);

    User getUserFromToken(String token);

    UserLoginResponse login(UserLoginRequest userLoginRequest);
}
