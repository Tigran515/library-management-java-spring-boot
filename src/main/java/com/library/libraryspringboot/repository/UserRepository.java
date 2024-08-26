package com.library.libraryspringboot.repository;

import com.library.libraryspringboot.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @NotNull Page<User> findAll(final @NotNull Pageable pageable);
    Optional<User> findByUsername(String username);
    Boolean existsUserByUsername(String username);
//    @Modifying
//    @Query("UPDATE User u SET u.name = ?1, u.lname = ?2, u.sname = ?3 WHERE u.username = ?4")
//    void updateNameLastNameSureNameByUsername(String name, String lastName, String sureName, String username);
}
