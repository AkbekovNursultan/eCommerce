package kg.alatoo.eCommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CartElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer quantity;
    private Integer price;
    private Integer total;
    @ManyToOne(cascade = CascadeType.ALL)
    private Purchase purchase;
    @ManyToOne(cascade = CascadeType.ALL)
    private Cart cart;
}
