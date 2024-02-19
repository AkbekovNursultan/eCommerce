package kg.alatoo.eCommerce.service;

import kg.alatoo.eCommerce.dto.category.CategoryRequest;
import kg.alatoo.eCommerce.dto.product.ProductRequest;


public interface ProductService {
    void addNewProduct(ProductRequest productRequest, String token);

    void addNewCategory(CategoryRequest categoryRequest, String token);

    void update(String token, Long productId, ProductRequest productRequest);
}
