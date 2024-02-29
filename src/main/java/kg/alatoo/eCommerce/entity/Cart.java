package kg.alatoo.eCommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer price;
    @OneToOne(mappedBy = "cart")
    private Customer customer;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> productList;
}
