package kg.alatoo.eCommerce.repository;

import kg.alatoo.eCommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByCustomerId(Long id);
}
