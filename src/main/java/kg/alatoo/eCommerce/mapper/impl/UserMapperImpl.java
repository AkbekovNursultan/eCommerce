package kg.alatoo.eCommerce.mapper.impl;

import kg.alatoo.eCommerce.dto.user.CustomerInfoResponse;
import kg.alatoo.eCommerce.dto.user.WorkerInfoResponse;
import kg.alatoo.eCommerce.entity.Customer;
import kg.alatoo.eCommerce.entity.User;
import kg.alatoo.eCommerce.entity.Worker;
import kg.alatoo.eCommerce.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public CustomerInfoResponse toDto(Customer customer) {
        CustomerInfoResponse response = new CustomerInfoResponse();
        User user = customer.getUser();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setProductList(customer.getCart().getProductList());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setCountry(customer.getCountry());
        response.setAddress(customer.getAddress());
        response.setCity(customer.getCity());
        response.setZipCode(customer.getZipCode());
        response.setPhone(customer.getPhone());
        response.setAdditionalInfo(customer.getAdditionalInfo());

        return response;
    }

    @Override
    public WorkerInfoResponse toDto(Worker worker) {
        WorkerInfoResponse response = new WorkerInfoResponse();
        User user = worker.getUser();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setAddedProductsList(worker.getAddedProductList());
        return response;
    }
    //Worker worker = user.getWorker();
    //return userMapper.toDto(worker);
}
