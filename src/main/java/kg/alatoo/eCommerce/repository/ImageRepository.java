package kg.alatoo.eCommerce.repository;

import kg.alatoo.eCommerce.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
