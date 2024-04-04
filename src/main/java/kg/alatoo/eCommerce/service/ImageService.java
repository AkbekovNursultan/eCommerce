package kg.alatoo.eCommerce.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void upload(String token, MultipartFile file, Long productId);
    void deleteFile(Long id);
}
