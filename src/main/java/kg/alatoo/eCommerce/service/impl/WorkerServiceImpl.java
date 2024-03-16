package kg.alatoo.eCommerce.service.impl;

import kg.alatoo.eCommerce.dto.user.ChangePasswordRequest;
import kg.alatoo.eCommerce.dto.user.WorkerInfoResponse;
import kg.alatoo.eCommerce.entity.User;
import kg.alatoo.eCommerce.entity.Worker;
import kg.alatoo.eCommerce.enums.Role;
import kg.alatoo.eCommerce.exception.BadRequestException;
import kg.alatoo.eCommerce.mapper.CustomerMapper;
import kg.alatoo.eCommerce.repository.UserRepository;
import kg.alatoo.eCommerce.service.AuthService;
import kg.alatoo.eCommerce.service.WorkerService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class WorkerServiceImpl implements WorkerService{
    private AuthService authService;
    private CustomerMapper customerMapper;
    private PasswordEncoder encoder;
    private UserRepository userRepository;

    @Override
    public WorkerInfoResponse workerInfo(String token) {
        User user = authService.getUserFromToken(token);
        if(!user.getRole().equals(Role.WORKER))
            throw new BadRequestException("You can't do this.");
        Worker worker = user.getWorker();
        return customerMapper.toDtoWorker(worker);
    }

    @Override
    public void changePassword(String token, ChangePasswordRequest request) {
        User user = authService.getUserFromToken(token);
        if(!user.getRole().equals(Role.WORKER))
            throw new BadRequestException("You have no permission");
        if(!encoder.matches(request.getCurrentPassword(), (user.getPassword())))
            throw new BadRequestException("Incorrect password.");
        if(request.getNewPassword().equals(request.getCurrentPassword()))
            throw new BadRequestException("This password is already in use!.");
        user.setPassword(encoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void update(String token, WorkerInfoResponse request) {
        User user = authService.getUserFromToken(token);
        Optional<User> user1 = userRepository.findByUsername(request.getUsername());
        if(!user.getRole().equals(Role.WORKER))
            throw new BadRequestException("You can't do this.");
        if(request.getUsername() != null){
            if(user1.isEmpty() || user1.get() == user)
                user.setUsername(request.getUsername());
            else
                throw new BadRequestException("This username already in use!");
        }
        if (request.getFirstName() != null)
            user.setFirstName(request.getFirstName());
        if (request.getLastName() != null)
            user.setLastName(request.getLastName());
        userRepository.save(user);
    }
}
