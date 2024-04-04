package kg.alatoo.eCommerce.mapper;

import kg.alatoo.eCommerce.dto.image.ImageResponse;
import kg.alatoo.eCommerce.entity.Image;

public interface ImageMapper {
    public ImageResponse toDetailDto(Image image);
}
