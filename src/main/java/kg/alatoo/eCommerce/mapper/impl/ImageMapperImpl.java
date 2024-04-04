package kg.alatoo.eCommerce.mapper.impl;

import kg.alatoo.eCommerce.dto.image.ImageResponse;
import kg.alatoo.eCommerce.entity.Image;
import kg.alatoo.eCommerce.mapper.ImageMapper;
import org.springframework.stereotype.Component;

@Component
public class ImageMapperImpl implements ImageMapper {
    @Override
    public ImageResponse toDetailDto(Image image) {
        ImageResponse response = new ImageResponse();
        response.setId(image.getId());
        response.setPath(image.getPath());
        response.setName(image.getName());
        response.setProductId(image.getProduct().getId());
        return response;
    }
}
