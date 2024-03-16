package kg.alatoo.eCommerce.controller;

import kg.alatoo.eCommerce.dto.email.EmailRequest;
import kg.alatoo.eCommerce.dto.email.RecoveryRequest;
import kg.alatoo.eCommerce.dto.email.VerificationRequest;
import kg.alatoo.eCommerce.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/email")
public class EmailController {
    private EmailService emailService;
    @PostMapping()
    public String code(@RequestBody EmailRequest request, @RequestHeader("Authorization") String token){
        emailService.sendVerificationCode(request, token);
        return "We have sent a code to your email!";
    }
    @PostMapping("/verify")
    public String verifyEmail(@RequestBody VerificationRequest request, @RequestHeader("Authorization") String token){
        return emailService.verifyEmail(request, token);
    }
    @PostMapping("/recovery")
    public String recovery(@RequestBody EmailRequest request){
        return emailService.recovery(request.getEmail());
    }
    @PostMapping("/password_recovery")
    public String recoverPassword(@RequestParam String code, @RequestBody RecoveryRequest request){
        return emailService.recoverPassword(code, request);
    }
}
