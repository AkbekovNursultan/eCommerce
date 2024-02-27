package kg.alatoo.eCommerce.service.impl;

import kg.alatoo.eCommerce.dto.user.ChangePasswordRequest;
import kg.alatoo.eCommerce.dto.user.CustomerInfoResponse;
import kg.alatoo.eCommerce.dto.user.WorkerInfoResponse;
import kg.alatoo.eCommerce.entity.Customer;
import kg.alatoo.eCommerce.entity.User;
import kg.alatoo.eCommerce.entity.Worker;
import kg.alatoo.eCommerce.enums.Role;
import kg.alatoo.eCommerce.exception.BadRequestException;
import kg.alatoo.eCommerce.mapper.UserMapper;
import kg.alatoo.eCommerce.repository.UserRepository;
import kg.alatoo.eCommerce.service.AuthService;
import kg.alatoo.eCommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
    private AuthService authService;
    private UserRepository userRepository;
    private PasswordEncoder encoder;

    @Override
    public CustomerInfoResponse customerInfo(String token) {
        User user = authService.getUserFromToken(token);
        Customer customer = user.getCustomer();
        return userMapper.toDto(customer);
    }
    @Override
    public WorkerInfoResponse workerInfo(String token) {
        User user = authService.getUserFromToken(token);
        Worker worker = user.getWorker();
        return userMapper.toDto(worker);
    }

    @Override
    public void update(String token, CustomerInfoResponse request) {
        User user = authService.getUserFromToken(token);
        Optional<User> user1 = userRepository.findByUsername(request.getUsername());
        if(request.getUsername() != null){
            if(user1.isEmpty() || user1.get() == user)
                user.setUsername(request.getUsername());
            else
                throw new BadRequestException("This username already in use!");
        }
        if(user.getRole().equals(Role.CUSTOMER)) {
            Customer customer = user.getCustomer();
            if (request.getEmail() != null)
                user.setEmail(request.getEmail());
            if (request.getCity() != null)
                customer.setCity(request.getCity());
            if (request.getCountry() != null)
                customer.setCountry(request.getCountry());
            if (request.getPhone() != null)
                customer.setPhone(request.getPhone());
            if (request.getFirstName() != null)
                user.setFirstName(request.getFirstName());
            if (request.getLastName() != null)
                user.setLastName(request.getLastName());
            if (request.getZipCode() != null)
                customer.setZipCode(request.getZipCode());
            if (request.getAddress() != null)
                customer.setAddress(request.getAddress());
            if (request.getAdditionalInfo() != null)
                customer.setAdditionalInfo(request.getAdditionalInfo());
            userRepository.save(user);
        }
        else {
            
        }
    }

    @Override
    public void changePassword(String token, ChangePasswordRequest request) {
        User user = authService.getUserFromToken(token);
        System.out.println(encoder.encode(request.getCurrentPassword()));

        System.out.println(user.getPassword());
        System.out.println();
        if(!encoder.matches(request.getCurrentPassword(), (user.getPassword())))
            throw new BadRequestException("Incorrect password.");
        if(request.getNewPassword().equals(request.getCurrentPassword()))
            throw new BadRequestException("This password is already in use!.");
        user.setPassword(encoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }


}
