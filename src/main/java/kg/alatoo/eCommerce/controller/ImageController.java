package kg.alatoo.eCommerce.controller;

import kg.alatoo.eCommerce.dto.image.ImageResponse;
import kg.alatoo.eCommerce.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private ImageService imageService;

    @PostMapping("/upload/{productId}")
    public String upload(@RequestHeader("Authorization") String token, @RequestParam(value = "file") MultipartFile file, @PathVariable Long productId){
        imageService.uploadImage(token, file, productId);
        return "Done";
    }
    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = imageService.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
    @GetMapping("{id}")
    public ImageResponse getById(@PathVariable Long id){
        return imageService.showById(id);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteFile(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        imageService.deleteFile(token, id);
        return "Image deleted successfully!";
    }
}
