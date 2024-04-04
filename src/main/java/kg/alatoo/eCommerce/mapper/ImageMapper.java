package kg.alatoo.eCommerce.mapper;

import kg.alatoo.eCommerce.dto.image.ImageResponse;
import kg.alatoo.eCommerce.entity.Image;

public interface ImageMapper {
    ImageResponse toDetailDto(Image image);
}
