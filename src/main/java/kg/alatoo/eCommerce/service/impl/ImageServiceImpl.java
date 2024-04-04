package kg.alatoo.eCommerce.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import kg.alatoo.eCommerce.dto.image.ImageResponse;
import kg.alatoo.eCommerce.entity.Image;
import kg.alatoo.eCommerce.entity.Product;
import kg.alatoo.eCommerce.entity.User;
import kg.alatoo.eCommerce.enums.Role;
import kg.alatoo.eCommerce.exception.BadRequestException;
import kg.alatoo.eCommerce.exception.NotFoundException;
import kg.alatoo.eCommerce.mapper.ImageMapper;
import kg.alatoo.eCommerce.repository.ImageRepository;
import kg.alatoo.eCommerce.repository.ProductRepository;
import kg.alatoo.eCommerce.service.AuthService;
import kg.alatoo.eCommerce.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Value("${location.path}")
    private String path;

    @Autowired
    private AmazonS3 s3Client;

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ImageMapper imageMapper;
    private final AuthService authService;

    @Override
    public String uploadImage(String token, MultipartFile file, Long productId) {
        User user = authService.getUserFromToken(token);
        if(user.getRole() != Role.WORKER)
            throw new BadRequestException("You can't do this.");
        Optional<Product> product = productRepository.findById(productId);
        if(product.isEmpty())
            throw new NotFoundException("The product not found.", HttpStatus.NOT_FOUND);
        if(product.get().getImage() != null) {
            deleteFile(token , product.get().getImage().getId());
            imageRepository.deleteById(product.get().getImage().getId());
        }
        Image image = saveImage(file);
        product.get().setImage(image);
        image.setProduct(product.get());
        imageRepository.save(image);
        productRepository.saveAndFlush(product.get());
        return "File uploaded : " + image.getName();
    }

    private Image saveImage(MultipartFile file) {
        Image image = new Image();

        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        image.setName(fileName);
        fileObj.delete();

        log.info("File with name = {} has successfully uploaded", image.getName());
        Image image1 = imageRepository.saveAndFlush(image);
        String url = path+image1.getId();
        image1.setPath(url);
        return imageRepository.saveAndFlush(image1);
    }

    @Override
    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ImageResponse showById(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        if(image.isEmpty())
            throw new NotFoundException("Image not found!", HttpStatus.NOT_FOUND);
        return imageMapper.toDetailDto(image.get());
    }
    @Override
    public String deleteFile(String token, Long id) {
        User user = authService.getUserFromToken(token);
        if(user.getRole() != Role.WORKER)
            throw new BadRequestException("You can't do this.");
        Optional<Image> image = imageRepository.findById(id);
        System.out.println(image);
        if(image.isEmpty())
            throw new NotFoundException("This image not found!", HttpStatus.NOT_FOUND);
        String fileName = image.get().getName();
        Optional<Product> product = productRepository.findById(image.get().getProduct().getId());
        if(product.isPresent()){
            product.get().setImage(null);
            image.get().setProduct(null);
        }
        productRepository.saveAndFlush(product.get());
        imageRepository.delete(image.get());
        s3Client.deleteObject(bucketName, fileName);
        return fileName + " removed ...";
    }


    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}