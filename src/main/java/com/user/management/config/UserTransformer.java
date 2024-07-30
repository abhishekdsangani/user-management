package com.user.management.config;

import com.user.management.dto.UserDto;
import com.user.management.model.UserData;
import org.springframework.stereotype.Component;

@Component
public class UserTransformer {

    public UserData toEntity(UserDto userDto){
        UserData userData = new UserData();
        userData.setUsername(userDto.getUsername());
        userData.setPassword(userDto.getPassword());
        userData.setEmail(userDto.getEmail());
        userData.setPhone(userDto.getPhone());
        userData.setName(userDto.getName());
        return userData;
    }
}
