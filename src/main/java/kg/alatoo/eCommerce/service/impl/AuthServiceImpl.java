package kg.alatoo.eCommerce.service.impl;

import kg.alatoo.eCommerce.config.JwtService;
import kg.alatoo.eCommerce.dto.UserLoginRequest;
import kg.alatoo.eCommerce.dto.UserLoginResponse;
import kg.alatoo.eCommerce.dto.UserRegisterRequest;
import kg.alatoo.eCommerce.entity.User;
import kg.alatoo.eCommerce.enums.Role;
import kg.alatoo.eCommerce.repository.UserRepository;
import kg.alatoo.eCommerce.service.AuthService;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private AuthenticationManager authenticationManager;
    @Override
    public void register(UserRegisterRequest userRegisterRequest) {
        if(userRepository.findByUsername(userRegisterRequest.getUsername()).isPresent())
            throw new BadCredentialsException("uf");
        User user = new User();
        user.setUsername(userRegisterRequest.getUsername());
        user.setPassword(userRegisterRequest.getPassword());
        if(!containsRole(userRegisterRequest.getRole()))
            throw new BadCredentialsException("no");
        user.setRole(Role.valueOf(userRegisterRequest.getRole().toUpperCase()));
        userRepository.save(user);
    }
    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        Optional<User> user = userRepository.findByUsername(userLoginRequest.getUsername());
        if(user.isEmpty())
            throw new NotFoundException("404");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(),userLoginRequest.getPassword()));

        }catch (org.springframework.security.authentication.BadCredentialsException e){
            throw new BadCredentialsException("user not found");
        }
        return convertToResponse(user);
    }
    @Override
    public User getUserFromToken(String token){

        String[] chunks = token.substring(7).split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        JSONParser jsonParser = new JSONParser();
        JSONObject object = null;
        try {
            object = (JSONObject) jsonParser.parse(decoder.decode(chunks[1]));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return userRepository.findByUsername(String.valueOf(object.get("sub"))).orElseThrow(() -> new RuntimeException("user can be null"));
    }

    private UserLoginResponse convertToResponse(Optional<User> user) {
        UserLoginResponse loginResponse = new UserLoginResponse();
        loginResponse.setUsername(user.get().getUsername());
        loginResponse.setId(user.get().getId());
        if (user.get().getRole().equals(Role.CUSTOMER))
            loginResponse.setUsername(user.get().getUsername());
        Map<String, Object> extraClaims = new HashMap<>();

        String token = jwtService.generateToken(extraClaims, user.get());
        loginResponse.setToken(token);

        return loginResponse;
    }

    private boolean containsRole(String role1) {
        for (Role role:Role.values()){
            if (role.name().equalsIgnoreCase(role1))
                return true;
        }
        return false;
    }
}
