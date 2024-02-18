package kg.alatoo.eCommerce.mapper;

import kg.alatoo.eCommerce.dto.user.UserInfoResponse;
import kg.alatoo.eCommerce.entity.User;

public interface UserMapper {
    UserInfoResponse toDto(User user);
}
