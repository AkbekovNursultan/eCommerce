package kg.alatoo.eCommerce.service.impl;

import kg.alatoo.eCommerce.dto.email.EmailRequest;
import kg.alatoo.eCommerce.dto.email.RecoveryRequest;
import kg.alatoo.eCommerce.dto.email.VerificationRequest;
import kg.alatoo.eCommerce.entity.User;
import kg.alatoo.eCommerce.exception.BadRequestException;
import kg.alatoo.eCommerce.exception.NotFoundException;
import kg.alatoo.eCommerce.repository.UserRepository;
import kg.alatoo.eCommerce.service.AuthService;
import kg.alatoo.eCommerce.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Autowired
    private final JavaMailSender mailSender;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    @Override
    public void sendVerificationCode(EmailRequest request, String token) {
        User user = authService.getUserFromToken(token);
        SimpleMailMessage message1 = new SimpleMailMessage();
        message1.setTo(request.getEmail());
        message1.setFrom("nursultan20052003@gmail.com");
        message1.setSubject("Verify your account");

        String code = generateCode();
        user.setVerificationCode(code);
        user.setEmail(request.getEmail());
        userRepository.save(user);
        message1.setText("\n\n" + code + "\n\n" + "This is code for verifying your account.\n\nDon't share it!!!");
        mailSender.send(message1);
    }
    @Override
    public String verifyEmail(VerificationRequest request, String token) {
        User user = authService.getUserFromToken(token);
        if(!user.getVerificationCode().equals(request.getVerificationCode()))
            throw new BadRequestException("Incorrect verification code.");
        user.setEmailVerified(true);
        userRepository.save(user);
        return "Email successfully connected.";
    }
    @Override
    public String recovery(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        String code = generateCode();
        if(user.isEmpty())
            throw new NotFoundException("Account with this email doesn't exist!", HttpStatus.NOT_FOUND);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nursultan20052003@gmail.com");
        message.setTo(email);
        message.setText("This is link for recovery your password: http://localhost:8080/email/password_recovery?code=" + code + "\n\nDon't share it!!!");
        message.setSubject("Password recovery.");
        mailSender.send(message);
        user.get().setRecoveryCode(code);
        userRepository.save(user.get());
        return "Message was sent to your email.";
    }

    @Override
    public String recoverPassword(String code, RecoveryRequest request) {
        Optional<User> user = userRepository.findByRecoveryCode(code);
        if(encoder.matches(request.getNewPassword(), (user.get().getPassword())))
            throw new BadRequestException("This password is already used.");
        if(!request.getNewPassword().equals(request.getConfirmPassword()))
            throw new BadRequestException("The passwords doesn't match.");
        user.get().setPassword(encoder.encode(request.getNewPassword()));
        userRepository.save(user.get());

        return "Password successfully changed";
    }

    private String generateCode(){
        String code = "";
        Random random = new Random();
        for(int k = 0; k < 6; k++) {
            if (random.nextInt(2) == 0)
                code += (char) (random.nextInt(26) + 65);
            else
                code += (char) (random.nextInt(10) + 48);
            }
        return code;
    }
}
