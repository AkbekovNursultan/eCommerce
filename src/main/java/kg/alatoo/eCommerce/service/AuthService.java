package kg.alatoo.eCommerce.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.alatoo.eCommerce.dto.user.UserLoginRequest;
import kg.alatoo.eCommerce.dto.user.UserLoginResponse;
import kg.alatoo.eCommerce.dto.user.UserRegisterRequest;
import kg.alatoo.eCommerce.entity.User;

import java.io.IOException;

public interface AuthService {
    void register(UserRegisterRequest userRegisterRequest);

    UserLoginResponse login(UserLoginRequest userLoginRequest);

    User getUserFromToken(String token);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
