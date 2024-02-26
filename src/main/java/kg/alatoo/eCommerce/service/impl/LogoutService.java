package kg.alatoo.eCommerce.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.alatoo.eCommerce.entity.Token;
import kg.alatoo.eCommerce.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication)
    {
        final String authHeader = request.getHeader("Authorization");
        final String token;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        token = authHeader.substring(7);
        Optional<Token> storedToken = tokenRepository.findByToken(token);
        if(storedToken.isPresent()){
            storedToken.get().setExpired(true);
            storedToken.get().setRevoked(true);
            tokenRepository.save(storedToken.get());

        }
    }
}
