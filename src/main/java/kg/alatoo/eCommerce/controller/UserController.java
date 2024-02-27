package kg.alatoo.eCommerce.controller;

import kg.alatoo.eCommerce.dto.user.ChangePasswordRequest;
import kg.alatoo.eCommerce.dto.user.CustomerInfoResponse;
import kg.alatoo.eCommerce.dto.user.WorkerInfoResponse;
import kg.alatoo.eCommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @GetMapping("/customer/info")
    public CustomerInfoResponse customerProfile(@RequestHeader("Authorization") String token){
        return userService.customerInfo(token);
    }
    @GetMapping("/worker/info")
    public WorkerInfoResponse workerProfile(@RequestHeader("Authorization") String token){
        return userService.workerInfo(token);
    }

    @PutMapping("/update")
    public String update(@RequestHeader("Authorization") String token, @RequestBody CustomerInfoResponse request){
        userService.update(token, request);
        return "Profile updated.";
    }

    @PutMapping("/change_password")
    public String changePassword(@RequestHeader("Authorization") String token, @RequestBody ChangePasswordRequest request){
        userService.changePassword(token, request);
        return "Password successfully changed.";
    }
}
