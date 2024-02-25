package kg.alatoo.eCommerce.service;

import kg.alatoo.eCommerce.dto.category.CategoryRequest;
import kg.alatoo.eCommerce.dto.product.ProductRequest;
import kg.alatoo.eCommerce.dto.product.ProductResponse;

import java.util.List;


public interface ProductService {
    void addNewProduct(ProductRequest productRequest, String token);

    void addNewCategory(CategoryRequest categoryRequest, String token);

    void update(String token, Long productId, ProductRequest productRequest);

    void restock(String token, Long productId, Integer addedProducts);

    List<ProductResponse> getAll();

    void buy(String token, Long productId, Integer quantity);
}
