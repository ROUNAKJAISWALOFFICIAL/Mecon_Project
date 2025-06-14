package com.asset.asset_management.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asset.asset_management.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
