package kg.alatoo.eCommerce.service;

import kg.alatoo.eCommerce.dto.category.CategoryRequest;
import kg.alatoo.eCommerce.dto.product.ProductAddRequest;
import org.springframework.stereotype.Service;


public interface ProductService {
    void addNewProduct(ProductAddRequest productAddRequest, String token);

    void addNewCategory(CategoryRequest categoryRequest, String token);
}
