package kg.alatoo.eCommerce.mapper.impl;

import kg.alatoo.eCommerce.dto.product.ProductResponse;
import kg.alatoo.eCommerce.entity.Product;
import kg.alatoo.eCommerce.enums.Color;
import kg.alatoo.eCommerce.enums.Size;
import kg.alatoo.eCommerce.enums.Tag;
import kg.alatoo.eCommerce.mapper.ProductMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapperImpl implements ProductMapper {
    @Override
    public List<ProductResponse> toDtoS(List<Product> all) {
        List<ProductResponse> productResponses = new ArrayList<>();
        for(Product product : all){
            productResponses.add(toDto(product));
        }
        return productResponses;
    }

    @Override
    public ProductResponse toDto(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setTitle(product.getTitle());
        productResponse.setCategory(product.getCategory().getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setTitle(product.getTitle());
        productResponse.setPrice(product.getPrice());
        productResponse.setSizes(product.getSizes());
        productResponse.setTags(product.getTags());
        productResponse.setColors(product.getColors());
        productResponse.setDescription(product.getDescription());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setCode(product.getCode());
        return productResponse;
    }
}
