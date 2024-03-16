package kg.alatoo.eCommerce.mapper;

import kg.alatoo.eCommerce.dto.user.CustomerInfoResponse;
import kg.alatoo.eCommerce.dto.user.WorkerInfoResponse;
import kg.alatoo.eCommerce.entity.Customer;
import kg.alatoo.eCommerce.entity.Worker;

public interface CustomerMapper {
    CustomerInfoResponse toDto(Customer customer);
    WorkerInfoResponse toDtoWorker(Worker worker);
}
