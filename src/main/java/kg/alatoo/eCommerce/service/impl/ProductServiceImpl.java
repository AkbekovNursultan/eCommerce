package kg.alatoo.eCommerce.service.impl;

import kg.alatoo.eCommerce.dto.category.CategoryRequest;
import kg.alatoo.eCommerce.dto.product.ProductAddRequest;
import kg.alatoo.eCommerce.entity.Category;
import kg.alatoo.eCommerce.entity.Product;
import kg.alatoo.eCommerce.entity.User;
import kg.alatoo.eCommerce.enums.Role;
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
    public void addNewProduct(ProductAddRequest productAddRequest, String token) {
        User user = authService.getUserFromToken(token);
        if(!user.getRole().equals(Role.WORKER))
            throw new BadCredentialsException("You have no permission");
        Product product = new Product();
        product.setTitle(productAddRequest.getTitle());
        product.setCode(productAddRequest.getCode());
        Optional<Category> category = categoryRepository.findByName(productAddRequest.getCategory());
        if(category.isEmpty())
            throw new NotFoundException(productAddRequest.getCategory() + "- doesn't exist.");
        product.setPrice(productAddRequest.getPrice());
        product.setDescription(productAddRequest.getDescription());

        product.setColors(productAddRequest.getColors());
        product.setTags(productAddRequest.getTags());
        product.setSizes(productAddRequest.getSizes());

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


}
