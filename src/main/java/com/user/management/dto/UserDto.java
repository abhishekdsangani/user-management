package com.user.management.dto;

import com.user.management.model.UserData;
import lombok.Data;

@Data
public class UserDto {
    
    private String username;
    private String password;
    private String email;
    private String phone;
    private String name;
}