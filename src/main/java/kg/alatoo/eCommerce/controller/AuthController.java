package kg.alatoo.eCommerce.controller;

import kg.alatoo.eCommerce.dto.UserLoginRequest;
import kg.alatoo.eCommerce.dto.UserRegisterRequest;
import kg.alatoo.eCommerce.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody UserRegisterRequest userRegisterRequest){
        authService.register(userRegisterRequest);
        return "Done";
    }
    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequest userLoginRequest){
        authService.login(userLoginRequest);
        return "Done";
    }

}
