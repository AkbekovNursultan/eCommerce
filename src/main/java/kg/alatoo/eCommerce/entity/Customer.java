package kg.alatoo.eCommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    private String address;
    private String city;
    private String zipCode;
    private String phone;
    private String additionalInfo;
    private Integer balance;
    @OneToOne(mappedBy = "customer")
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;

}
