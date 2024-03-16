package kg.alatoo.eCommerce.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.alatoo.eCommerce.config.JwtService;
import kg.alatoo.eCommerce.dto.user.UserLoginRequest;
import kg.alatoo.eCommerce.dto.user.UserLoginResponse;
import kg.alatoo.eCommerce.dto.user.UserRegisterRequest;
import kg.alatoo.eCommerce.entity.*;
import kg.alatoo.eCommerce.enums.Role;
import kg.alatoo.eCommerce.enums.TokenType;
import kg.alatoo.eCommerce.exception.BadCredentialsException;
import kg.alatoo.eCommerce.exception.BadRequestException;
import kg.alatoo.eCommerce.repository.TokenRepository;
import kg.alatoo.eCommerce.repository.UserRepository;
import kg.alatoo.eCommerce.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    @Override
    public void register(UserRegisterRequest userRegisterRequest) {
        if(userRepository.findByUsername(userRegisterRequest.getUsername()).isPresent())
            throw new BadRequestException("Already exists");
        User user = new User();
        user.setUsername(userRegisterRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        if(!containsRole(userRegisterRequest.getRole()))
            throw new BadRequestException("Unknown role.");
        user.setRole(Role.valueOf(userRegisterRequest.getRole().toUpperCase()));
        if(user.getRole().equals(Role.CUSTOMER)){
            Customer customer = new Customer();
            customer.setCountry(userRegisterRequest.getCountry());
            customer.setCity(userRegisterRequest.getCity());
            customer.setAddress(userRegisterRequest.getAddress());
            customer.setAdditionalInfo(userRegisterRequest.getAdditionalInfo());
            customer.setPhone(userRegisterRequest.getPhone());
            customer.setId(user.getId());
            customer.setZipCode(userRegisterRequest.getZipCode());
            customer.setUser(user);
            customer.setBalance(10000);
            List<Product> favoritesList = new ArrayList<>();
            customer.setFavoritesList(favoritesList);
            Cart cart = new Cart();
            customer.setCart(cart);
            user.setCustomer(customer);
        } else if (user.getRole().equals(Role.WORKER)) {
            Worker worker = new Worker();
            worker.setUser(user);
            user.setWorker(worker);
        }
        user.setEmailVerified(false);
        userRepository.save(user);
    }
    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        Optional<User> user = userRepository.findByUsername(userLoginRequest.getUsername());
        if(user.isEmpty())
            throw new BadRequestException("User not found.");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(),userLoginRequest.getPassword()));

        }catch (org.springframework.security.authentication.BadCredentialsException e){
            throw new BadRequestException("Invalid password.");
        }
        return convertToResponse(user);
    }
    @Override
    public User getUserFromToken(String token){

        String[] chunks = token.substring(7).split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        if (chunks.length != 3)
            throw new BadCredentialsException("Wrong token!");
        JSONParser jsonParser = new JSONParser();
        JSONObject object = null;
        try {
            byte[] decodedBytes = decoder.decode(chunks[1]);
            object = (JSONObject) jsonParser.parse(decodedBytes);
        } catch (ParseException e) {
            throw new BadCredentialsException("Wrong token!!");
        }
        return userRepository.findByUsername(String.valueOf(object.get("sub"))).orElseThrow(() -> new BadCredentialsException("Wrong token!!!"));
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader("Authorization");
        final String refreshToken;
        final String username;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            //return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if(username != null){
            var user = this.userRepository.findByUsername(username).orElseThrow();
            if(jwtService.isTokenValid(refreshToken, user)){
                var accessToken = jwtService.generateToken(user);
                UserLoginResponse authResponse = new UserLoginResponse();
                authResponse.setAccessToken(accessToken);
                authResponse.setRefreshToken(refreshToken);
                revokeAllUserTokens(user);
                saveToken(user, accessToken);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private UserLoginResponse convertToResponse(Optional<User> user) {
        UserLoginResponse loginResponse = new UserLoginResponse();

        Map<String, Object> extraClaims = new HashMap<>();

        String jwtToken = jwtService.generateToken(extraClaims, user.get());
        String refreshToken = jwtService.generateRefreshToken(user.get());
        loginResponse.setAccessToken(jwtToken);
        loginResponse.setRefreshToken(refreshToken);
        revokeAllUserTokens(user.get());
        saveToken(user.get(), jwtToken);
        return loginResponse;
    }

    private void saveToken(User user, String jwtToken){
        Token token = new Token();
        token.setToken(jwtToken);
        token.setUser(user);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user){
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if(validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(t ->{
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private boolean containsRole(String role1) {
        for (Role role:Role.values()){
            if (role.name().equalsIgnoreCase(role1))
                return true;
        }
        return false;
    }
}
