package kg.alatoo.eCommerce.service.impl;

import kg.alatoo.eCommerce.dto.category.CategoryRequest;
import kg.alatoo.eCommerce.dto.product.ProductRequest;
import kg.alatoo.eCommerce.dto.product.ProductResponse;
import kg.alatoo.eCommerce.entity.*;
import kg.alatoo.eCommerce.enums.Role;
import kg.alatoo.eCommerce.exception.BadRequestException;
import kg.alatoo.eCommerce.mapper.ProductMapper;
import kg.alatoo.eCommerce.repository.CartRepository;
import kg.alatoo.eCommerce.repository.CategoryRepository;
import kg.alatoo.eCommerce.repository.ProductRepository;
import kg.alatoo.eCommerce.repository.UserRepository;
import kg.alatoo.eCommerce.service.AuthService;
import kg.alatoo.eCommerce.service.ProductService;
import lombok.AllArgsConstructor;
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
    private final ProductMapper productMapper;
    private final CartRepository cartRepository;
    @Override
    public void addNewProduct(ProductRequest productRequest, String token) {
        User user = authService.getUserFromToken(token);
        if(!user.getRole().equals(Role.WORKER))
            throw new BadRequestException("You have no permission");
        Product product = new Product();
        product.setTitle(productRequest.getTitle());
        product.setCode(productRequest.getCode());
        Optional<Category> category = categoryRepository.findByName(productRequest.getCategory());
        if(category.isEmpty())
            throw new BadRequestException("The category '" + productRequest.getCategory() + "' doesn't exist.");
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setColors(productRequest.getColors());
        product.setTags(productRequest.getTags());
        product.setSizes(productRequest.getSizes());
        product.setWorker(user.getWorker());
        product.setQuantity(productRequest.getQuantity());
        if(product.getQuantity() < 0)
            product.setQuantity(0);
        List<Product> products = new ArrayList<>();
        if(!category.get().getProducts().isEmpty())
            products = category.get().getProducts();
        product.setCategory(category.get());
        products.add(product);
        category.get().setProducts(products);
        categoryRepository.saveAndFlush(category.get());
    }

    @Override
    public void addNewCategory(String token, CategoryRequest categoryRequest) {
        User user = authService.getUserFromToken(token);
        if(!user.getRole().equals(Role.WORKER))
            throw new BadRequestException("You have no permission.");
        Category category = new Category();
        if(categoryRepository.findByName(categoryRequest.getName()).isPresent())
            throw new BadRequestException("Already exists.");
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
    }

    @Override
    public void update(String token, Long productId, ProductRequest request) {
        User user = authService.getUserFromToken(token);
        if(!user.getRole().equals(Role.WORKER))
            throw new BadRequestException("You have no permission.");
        Optional<Product> product = productRepository.findById(productId);
        if(product.isEmpty())
            throw new BadRequestException("Incorrect productId.");
        if(!product.get().getWorker().equals(user.getWorker()))
            throw new BadRequestException("You have no permission.");
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
        if(request.getQuantity() < 0)
            throw new BadRequestException("Invalid quantity definition.");
        if(request.getQuantity() != null)
            product.get().setQuantity(request.getQuantity());
        if(request.getDescription() != null)
            product.get().setDescription(request.getDescription());
        productRepository.save(product.get());
    }

    @Override
    public List<ProductResponse> getAll() {
        List<Product> all = productRepository.findAll();
        return productMapper.toDtoS(all);
    }

    @Override
    public void buy(String token, Long productId, Integer quantity) {
        User user = authService.getUserFromToken(token);
        Customer customer = user.getCustomer();
        if(!user.getRole().equals(Role.CUSTOMER))
            throw new BadRequestException("You aren't allowed to do this.");
        Optional<Product> product = productRepository.findById(productId);
        if(product.isEmpty())
            throw new NotFoundException("Product with Id:" +productId+ " doesn't exist.");
        Cart cart = cartRepository.findByCustomerId(user.getCustomer().getId());
        if(cart.getPrice() == null)
            cart.setPrice(0);
        if(customer.getBalance() < cart.getPrice() + product.get().getPrice())
            throw new BadRequestException("You don't have enough money on your balance. \n(poor bastard)");
        customer.setBalance(user.getCustomer().getBalance() - product.get().getPrice());
        List<Product> list = cart.getProductList();
        list.add(product.get());
        cart.setProductList(list);
        cartRepository.saveAndFlush(cart);
        userRepository.saveAndFlush(user);
    }


}
