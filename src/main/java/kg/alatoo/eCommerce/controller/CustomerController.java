package kg.alatoo.eCommerce.controller;

import kg.alatoo.eCommerce.dto.product.ProductDetailsResponse;
import kg.alatoo.eCommerce.dto.product.ProductResponse;
import kg.alatoo.eCommerce.dto.user.ChangePasswordRequest;
import kg.alatoo.eCommerce.dto.user.CustomerInfoResponse;
import kg.alatoo.eCommerce.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {
    private CustomerService customerService;

    @GetMapping("/info")
    public CustomerInfoResponse customerProfile(@RequestHeader("Authorization") String token){
        return customerService.customerInfo(token);
    }

    @PutMapping("/update")
    public String update(@RequestHeader("Authorization") String token, @RequestBody CustomerInfoResponse request){
        customerService.update(token, request);
        return "Profile updated.";
    }

    @PutMapping("/change_password")
    public String changePassword(@RequestHeader("Authorization") String token, @RequestBody ChangePasswordRequest request){
        customerService.changePassword(token, request);
        return "Password successfully changed.";
    }

    @GetMapping("/favorites")
    public List<ProductResponse> favorites(@RequestHeader("Authorization") String token){
        return customerService.getFavorites(token);
    }
    @PostMapping("/add_favorite/{productId}")
    public String addFavorite(@RequestHeader("Authorization") String token, @PathVariable Long productId){
        customerService.addFavorite(token, productId);
        return "Done";
    }

    @DeleteMapping("/delete_favorite/{productId}")
    public String deleteFavorite(@RequestHeader("Authorization") String token, @PathVariable Long productId){
        customerService.deleteFavorite(token, productId);
        return "Done";
    }

    //+
}
