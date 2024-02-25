package kg.alatoo.eCommerce.mapper.impl;

import kg.alatoo.eCommerce.dto.user.UserInfoResponse;
import kg.alatoo.eCommerce.entity.User;
import kg.alatoo.eCommerce.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserInfoResponse toDto(User user) {
        UserInfoResponse response = new UserInfoResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setProductList(user.getProductList());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setCountry(user.getCustomer().getCountry());
        response.setAddress(user.getCustomer().getAddress());
        response.setCity(user.getCustomer().getCity());
        response.setZipCode(user.getCustomer().getZipCode());
        response.setPhone(user.getCustomer().getPhone());
        response.setAdditionalInfo(user.getCustomer().getAdditionalInfo());
        return response;
    }
}
