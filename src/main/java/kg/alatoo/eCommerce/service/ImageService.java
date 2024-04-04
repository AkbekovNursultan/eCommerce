package kg.alatoo.eCommerce.service;

import kg.alatoo.eCommerce.dto.image.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String uploadImage(String token, MultipartFile file, Long id);
    ImageResponse showById(Long id);
    byte[] downloadFile(String fileName);
    String deleteFile(String token, Long id);
}
