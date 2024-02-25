package kg.alatoo.eCommerce.controller;

import kg.alatoo.eCommerce.dto.category.CategoryRequest;
import kg.alatoo.eCommerce.dto.product.ProductRequest;
import kg.alatoo.eCommerce.dto.product.ProductResponse;
import kg.alatoo.eCommerce.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    @GetMapping("/all")
    public List<ProductResponse> responseList(){
        return productService.getAll();
    }
    @PostMapping("/add_category")
    public String addCategory(@RequestHeader("Authorization-Bearer") String token, @RequestBody CategoryRequest categoryRequest){
        productService.addNewCategory(categoryRequest, token);
        return "Category successfully added.";
    }
    @PostMapping("/add_new_product")
    public String addProduct(@RequestHeader("Authorization-Bearer") String token, @RequestBody ProductRequest productRequest){
        productService.addNewProduct(productRequest, token);
        return "Product was added.";
    }
    @PutMapping("/update/{productId}")
    public String updateProduct(@RequestHeader("Authorization-Bearer") String token, @PathVariable Long productId, @RequestBody ProductRequest productRequest){
        productService.update(token, productId, productRequest);
        return "Done";
    }
    @PutMapping("/restock/{productId}")
    public String restock(@RequestHeader("Authorizatiion - Bearer") String token, @PathVariable Long productId, @RequestParam Integer addedProducts){
        productService.restock(token, productId, addedProducts);
        return "Done";
    }
    @PostMapping("/buy/{productId}")
    public String buy(@RequestHeader("Authorization-Header")String token, @PathVariable Long productId, @RequestParam Integer quantity){
        productService.buy(token, productId, quantity);
        return "Purchase has been made successfully!";
    }

}
