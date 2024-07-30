package com.user.management.repository;

import com.user.management.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserData, Long> {
    UserData findByUsername(String username);

    void deleteUserById(Long userId);
}