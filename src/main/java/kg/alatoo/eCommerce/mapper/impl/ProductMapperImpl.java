package kg.alatoo.eCommerce.mapper.impl;

import kg.alatoo.eCommerce.dto.product.ProductDetailsResponse;
import kg.alatoo.eCommerce.dto.product.ProductResponse;
import kg.alatoo.eCommerce.entity.Product;
import kg.alatoo.eCommerce.mapper.ProductMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapperImpl implements ProductMapper {
    @Override
    public List<ProductResponse> toDtoS(List<Product> all) {
        List<ProductResponse> productResponse = new ArrayList<>();
        for(Product product : all){
            productResponse.add(toDto(product));
        }
        return productResponse;
    }
    public ProductResponse toDto(Product product){
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setTitle(product.getTitle());
        response.setPrice(product.getPrice());
        response.setCategory(product.getCategory().getName());
        response.setQuantity(product.getQuantity());
        return response;
    }

    @Override
    public ProductDetailsResponse toDetailDto(Product product) {
        ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();
        productDetailsResponse.setId(product.getId());
        productDetailsResponse.setTitle(product.getTitle());
        productDetailsResponse.setCategory(product.getCategory().getName());
        productDetailsResponse.setPrice(product.getPrice());
        productDetailsResponse.setTitle(product.getTitle());
        productDetailsResponse.setPrice(product.getPrice());
        productDetailsResponse.setSizes(product.getSizes());
        productDetailsResponse.setTags(product.getTags());
        productDetailsResponse.setColors(product.getColors());
        productDetailsResponse.setDescription(product.getDescription());
        productDetailsResponse.setQuantity(product.getQuantity());
        productDetailsResponse.setCode(product.getCode());
        return productDetailsResponse;
    }
}
