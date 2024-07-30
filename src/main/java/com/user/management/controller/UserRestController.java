package com.user.management.controller;

//import com.user.management.config.TokenProvider;

import com.user.management.config.TokenProvider;
import com.user.management.dto.AuthToken;
import com.user.management.dto.LoginUser;
import com.user.management.dto.UserDto;
import com.user.management.model.UserData;
import com.user.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserRestController {

    private final UserService userService;
    private final TokenProvider jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "/authenticate")
    public AuthToken generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("USER");

        return new AuthToken(token, role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody UserDto user) {
        try {
            if (userService.findOne(user.getUsername()) != null) {
                return ResponseEntity.status(409).body("Username already exists");
            }
            UserData createdUser = userService.createAdminOrUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body("Failed to create user");
        }
    }


    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public List<UserData> getAllUsers() {
        return userService.findAll();
    }


    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.ok().body("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete user");
        }
    }

    @PostMapping(value = "/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body("User logged out successfully");
    }
}