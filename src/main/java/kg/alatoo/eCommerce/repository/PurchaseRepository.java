package kg.alatoo.eCommerce.repository;

import kg.alatoo.eCommerce.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
