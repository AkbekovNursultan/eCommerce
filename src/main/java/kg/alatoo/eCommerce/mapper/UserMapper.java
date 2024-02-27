package kg.alatoo.eCommerce.mapper;

import kg.alatoo.eCommerce.dto.user.CustomerInfoResponse;
import kg.alatoo.eCommerce.dto.user.WorkerInfoResponse;
import kg.alatoo.eCommerce.entity.Customer;
import kg.alatoo.eCommerce.entity.User;
import kg.alatoo.eCommerce.entity.Worker;

public interface UserMapper {
    CustomerInfoResponse toDto(Customer customer);
    WorkerInfoResponse toDto(Worker worker);
}
