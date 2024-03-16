package kg.alatoo.eCommerce.dto.product;

import kg.alatoo.eCommerce.enums.Color;
import kg.alatoo.eCommerce.enums.Size;
import kg.alatoo.eCommerce.enums.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDetailsResponse {
    private Long id;
    private String title;
    private Integer price;
    private String category;
    private List<Size> sizes;
    private List<Tag> tags;
    private List<Color> colors;
    private String description;
    private Integer quantity;
    private String code;
}
