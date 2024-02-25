package kg.alatoo.eCommerce.mapper;

import kg.alatoo.eCommerce.dto.product.ProductResponse;
import kg.alatoo.eCommerce.entity.Product;
import kg.alatoo.eCommerce.mapper.impl.ProductMapperImpl;

import java.util.List;

public interface ProductMapper {
    List<ProductResponse> toDtoS(List<Product> all);

    ProductResponse toDto(Product product);
}
