package kg.alatoo.eCommerce.controller;

import kg.alatoo.eCommerce.dto.user.ChangePasswordRequest;
import kg.alatoo.eCommerce.dto.user.UserInfoResponse;
import kg.alatoo.eCommerce.mapper.UserMapper;
import kg.alatoo.eCommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private UserMapper usermapper;

    @GetMapping("/info")
    public UserInfoResponse profile(@RequestHeader("/authorization") String token){
        return userService.userInfo(token);
    }

    @PutMapping("/update")
    public String update(@RequestHeader("/authorization") String token, @RequestBody UserInfoResponse request){
        userService.update(token, request);
        return "Profile updated.";
    }

    @PutMapping("/change_password")
    public String changePassword(@RequestHeader("/authorization") String token, @RequestBody ChangePasswordRequest request){
        userService.changePassword(token, request);
        return "Password successfully changed.";
    }
}
