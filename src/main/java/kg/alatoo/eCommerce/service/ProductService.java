package kg.alatoo.eCommerce.service;

import kg.alatoo.eCommerce.dto.category.CategoryRequest;
import kg.alatoo.eCommerce.dto.product.ProductRequest;
import kg.alatoo.eCommerce.dto.product.ProductDetailsResponse;
import kg.alatoo.eCommerce.dto.product.ProductResponse;

import java.util.List;


public interface ProductService {
    void addNewProduct(ProductRequest productRequest, String token);

    void addNewCategory(String token, CategoryRequest request);

    void update(String token, Long productId, ProductRequest productRequest);

    List<ProductResponse> getAll();

    ProductDetailsResponse showById(Long id);
}
