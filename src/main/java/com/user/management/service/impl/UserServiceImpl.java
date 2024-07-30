package com.user.management.service.impl;

import com.user.management.config.UserTransformer;
import com.user.management.dto.UserDto;
import com.user.management.model.UserData;
import com.user.management.repository.UserRepository;
import com.user.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final UserTransformer userTransformer;
    private final BCryptPasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData userData = userRepository.findByUsername(username);
        if(userData == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(userData.getUsername(), userData.getPassword(), getAuthority(userData));
    }

    private Set<SimpleGrantedAuthority> getAuthority(UserData userData) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        if (userData.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    @Override
    public List<UserData> findAll() {
        List<UserData> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public UserData findOne(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserData createAdminOrUser(UserDto user) {
        UserData userData = userTransformer.toEntity(user);
        userData.setPassword(bcryptEncoder.encode(user.getPassword()));

        boolean isAdmin = userData.getUsername().contains("admin") || userData.getUsername().contains("superuser");
        userData.setAdmin(isAdmin);
        return userRepository.save(userData);
    }
}
