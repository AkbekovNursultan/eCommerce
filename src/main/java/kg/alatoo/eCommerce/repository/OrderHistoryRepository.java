package kg.alatoo.eCommerce.repository;

import kg.alatoo.eCommerce.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
}
