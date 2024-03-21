package kg.alatoo.eCommerce.controller;

import kg.alatoo.eCommerce.dto.cart.CartInfoResponse;
import kg.alatoo.eCommerce.dto.cart.OrderHistoryResponse;
import kg.alatoo.eCommerce.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;
    @GetMapping("/show")
    public CartInfoResponse info(@RequestHeader("Authorization")String token){
        return cartService.info(token);
    }
    @PostMapping("/add/{productId}")
    public String add(@RequestHeader("Authorization")String token, @PathVariable Long productId, @RequestParam Integer quantity){
        cartService.add(token, productId, quantity);
        return "Purchase has been made successfully!";
    }

    @DeleteMapping("/delete/{productId}")
    public String delete(@RequestHeader("Authorization")String token, @PathVariable Long productId){
        cartService.delete(token, productId);
        return "Product successfully deleted from your cart";
    }
    @PostMapping("/buy")
    public String buy(@RequestHeader("Authorization") String token){
        cartService.buy(token);
        return "Done";
    }
    @GetMapping("/history")
    public OrderHistoryResponse history(@RequestHeader("Authorization") String token){
        return cartService.history(token);
    }
}
