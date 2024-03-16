package kg.alatoo.eCommerce.mapper;

import kg.alatoo.eCommerce.dto.product.ProductDetailsResponse;
import kg.alatoo.eCommerce.dto.product.ProductResponse;
import kg.alatoo.eCommerce.entity.Product;

import java.util.List;

public interface ProductMapper {
    List<ProductResponse> toDtoS(List<Product> all);

    ProductDetailsResponse toDetailDto(Product product);
}
