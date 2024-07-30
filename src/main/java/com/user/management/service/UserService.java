package com.user.management.service;

import com.user.management.dto.UserDto;
import com.user.management.model.UserData;

import java.util.List;

public interface UserService {
    void deleteUserById(Long userId);

    UserData createAdminOrUser(UserDto user);

    List<UserData> findAll();

    UserData findOne(String username);
}
