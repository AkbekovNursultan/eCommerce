package kg.alatoo.eCommerce.service.impl;

import kg.alatoo.eCommerce.dto.category.CategoryRequest;
import kg.alatoo.eCommerce.dto.product.ProductRequest;
import kg.alatoo.eCommerce.entity.Category;
import kg.alatoo.eCommerce.entity.Product;
import kg.alatoo.eCommerce.entity.User;
import kg.alatoo.eCommerce.enums.Role;
import kg.alatoo.eCommerce.exception.BadRequestException;
import kg.alatoo.eCommerce.exception.BlockedException;
import kg.alatoo.eCommerce.repository.CategoryRepository;
import kg.alatoo.eCommerce.repository.ProductRepository;
import kg.alatoo.eCommerce.repository.UserRepository;
import kg.alatoo.eCommerce.service.AuthService;
import kg.alatoo.eCommerce.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    @Override
    public void addNewProduct(ProductRequest productRequest, String token) {
        User user = authService.getUserFromToken(token);
        if(!user.getRole().equals(Role.WORKER))
            throw new BadCredentialsException("You have no permission");
        Product product = new Product();
        product.setTitle(productRequest.getTitle());
        product.setCode(productRequest.getCode());
        Optional<Category> category = categoryRepository.findByName(productRequest.getCategory());
        if(category.isEmpty())
            throw new NotFoundException(productRequest.getCategory() + "- doesn't exist.");
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setColors(productRequest.getColors());
        product.setTags(productRequest.getTags());
        product.setSizes(productRequest.getSizes());
        product.setWorker(user.getWorker());
        List<Product> products = new ArrayList<>();
        if(category.get().getProducts().isEmpty())
            products = category.get().getProducts();
        products.add(product);

        category.get().setProducts(products);
        categoryRepository.save(category.get());
    }

    @Override
    public void addNewCategory(CategoryRequest categoryRequest, String token) {
        Category category = new Category();
        if(categoryRepository.findByName(categoryRequest.getName()).isEmpty())
            throw new BadCredentialsException("Already exists.");
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
    }

    @Override
    public void update(String token, Long productId, ProductRequest request) {
        User user = authService.getUserFromToken(token);
        if(!user.getRole().equals(Role.WORKER))
            throw new BlockedException("You have no permission.");
        Optional<Product> product = productRepository.findById(productId);
        if(product.isEmpty())
            throw new NotFoundException("404");
        if(!product.get().getWorker().equals(user.getWorker()))
            throw new BlockedException("You have no permission.");
        if(request.getCategory() != null) {
            Optional<Category> category = categoryRepository.findByName(request.getCategory());
            if (category.isEmpty())
                throw new BadRequestException("This category doesn't exist");
            product.get().setCategory(category.get());
        }

        if(request.getCode() != null)
            product.get().setCode(request.getCode());
        if(request.getSizes() != null)
            product.get().setSizes(request.getSizes());
        if(request.getPrice() != null)
            product.get().setPrice(request.getPrice());
        if(request.getTags() != null)
            product.get().setTags(request.getTags());
        if(request.getColors() != null)
            product.get().setColors(request.getColors());
        if(request.getTitle() != null)
            product.get().setTitle(request.getTitle());
        if(request.getDescription() != null)
            product.get().setDescription(request.getDescription());
        productRepository.save(product.get());
    }


}
