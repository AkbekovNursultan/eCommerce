package kg.alatoo.eCommerce.dto.product;

import jakarta.persistence.ManyToOne;
import kg.alatoo.eCommerce.entity.Category;
import kg.alatoo.eCommerce.entity.User;
import kg.alatoo.eCommerce.enums.Color;
import kg.alatoo.eCommerce.enums.Size;
import kg.alatoo.eCommerce.enums.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductAddRequest {
    private String title;
    private Integer price;
    private List<Size> sizes;
    private List<Tag> tags;
    private List<Color> colors;
    private String description;
    private User user;
    private String category;
    private String code;
}
