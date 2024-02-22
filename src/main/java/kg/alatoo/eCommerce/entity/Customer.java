package kg.alatoo.eCommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    private Long id;
    private String country;
    private String address;
    private String city;
    private String zipCode;
    private String phone;
    private String additionalInfo;
    @OneToOne(mappedBy = "customer")
    private User user;
}
