package kg.alatoo.eCommerce.controller;

import kg.alatoo.eCommerce.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private ImageService imageService;
    @PostMapping("/uplaod/{productId}")
    public String upload(@RequestHeader("/authorization") String token, @RequestParam(value = "file") MultipartFile file, @PathVariable Long productId){
        imageService.upload(token, file, productId);
        return "Done";
    }

}
