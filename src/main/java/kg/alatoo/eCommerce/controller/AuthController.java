package kg.alatoo.eCommerce.controller;

import kg.alatoo.eCommerce.dto.user.UserLoginRequest;
import kg.alatoo.eCommerce.dto.user.UserLoginResponse;
import kg.alatoo.eCommerce.dto.user.UserRegisterRequest;
import kg.alatoo.eCommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody UserRegisterRequest userRegisterRequest){
        authService.register(userRegisterRequest);
        return "User added successfully!";
    }
    @GetMapping("/login")
    public UserLoginResponse login(@RequestBody UserLoginRequest userLoginRequest){
        return authService.login(userLoginRequest);
    }
}
//+