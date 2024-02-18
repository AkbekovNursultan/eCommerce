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
        response.setCountry(user.getCountry());
        response.setAddress(user.getAddress());
        response.setCity(user.getCity());
        response.setZipCode(user.getZipCode());
        response.setPhone(user.getPhone());
        response.setAdditionalInfo(user.getAdditionalInfo());
        return response;
    }
}
