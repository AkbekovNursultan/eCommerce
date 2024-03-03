package kg.alatoo.eCommerce.service;

import kg.alatoo.eCommerce.dto.user.ChangePasswordRequest;
import kg.alatoo.eCommerce.dto.user.WorkerInfoResponse;

public interface WorkerService {
    WorkerInfoResponse workerInfo(String token);

    void changePassword(String token, ChangePasswordRequest request);

    void update(String token, WorkerInfoResponse request);
}
