package kg.alatoo.eCommerce.controller;

import kg.alatoo.eCommerce.dto.user.ChangePasswordRequest;
import kg.alatoo.eCommerce.dto.user.WorkerInfoResponse;
import kg.alatoo.eCommerce.service.WorkerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/worker")
public class WorkerController {
    private final WorkerService workerService;

    @GetMapping("/info")
    public WorkerInfoResponse workerProfile(@RequestHeader("Authorization") String token){
        return workerService.workerInfo(token);
    }

    @PutMapping("/update")
    public String update(@RequestHeader("Authorization") String token, @RequestBody WorkerInfoResponse request){
        workerService.update(token, request);
        return "Profile updated.";
    }

    @PutMapping("/change_password")
    public String changePassword(@RequestHeader("Authorization") String token, @RequestBody ChangePasswordRequest request){
        workerService.changePassword(token, request);
        return "Password successfully changed.";
    }
}//+
