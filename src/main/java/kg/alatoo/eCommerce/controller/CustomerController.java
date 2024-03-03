package kg.alatoo.eCommerce.controller;

import kg.alatoo.eCommerce.dto.user.ChangePasswordRequest;
import kg.alatoo.eCommerce.dto.user.CustomerInfoResponse;
import kg.alatoo.eCommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {
    private UserService userService;

    @GetMapping("/info")
    public CustomerInfoResponse customerProfile(@RequestHeader("Authorization") String token){
        return userService.customerInfo(token);
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

    @PostMapping("/add_favorite/{productId}")
    public String addFavorite(@RequestHeader("Authorization") String token, @PathVariable Long productId){
        userService.addFavorite(token, productId);
        return "Done";
    }
    //+
}