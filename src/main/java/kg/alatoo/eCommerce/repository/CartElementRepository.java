package kg.alatoo.eCommerce.repository;

import kg.alatoo.eCommerce.entity.Cart;
import kg.alatoo.eCommerce.entity.CartElement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartElementRepository extends JpaRepository<CartElement, Long> {
    CartElement findByCart(Cart cart);
}
