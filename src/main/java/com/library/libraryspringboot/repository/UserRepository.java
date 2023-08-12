package com.library.libraryspringboot.repository;

import com.library.libraryspringboot.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Integer> {
    Optional<User> findByUsername(String login);

    @Modifying
    @Query("UPDATE User u SET u.name = ?1, u.lname = ?2, u.sname = ?3 WHERE u.username = ?4")
    void updateNameLastNameSureNameByUsername(String name, String lastName, String sureName, String username);
}
