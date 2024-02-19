package kg.alatoo.eCommerce.controller;

import kg.alatoo.eCommerce.dto.category.CategoryRequest;
import kg.alatoo.eCommerce.dto.product.ProductRequest;
import kg.alatoo.eCommerce.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    @PostMapping("/add_category")
    public String addCategory(@RequestHeader("/authorization") String token, @RequestBody CategoryRequest categoryRequest){
        productService.addNewCategory(categoryRequest, token);
        return "Category successfully added.";
    }
    @PostMapping("/add_new_product")
    public String addProduct(@RequestHeader("/authorization") String token, @RequestBody ProductRequest productRequest){
        productService.addNewProduct(productRequest, token);
        return "Product was added.";
    }
    @PutMapping("/update/{productId}")
    public String updateProduct(@RequestHeader("/authorization") String token, @PathVariable Long productId, @RequestBody ProductRequest productRequest){
        productService.update(token, productId, productRequest);
        return "Done";
    }

}
