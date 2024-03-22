package com.library.libraryspringboot.service;

import com.library.libraryspringboot.dto.UserDTO;
import com.library.libraryspringboot.dto.UserRegistrationRequest;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<UserDTO> getUsers(final Integer pageNumber, final Integer size);
    UserDTO getUserById(Integer id);
    UserDTO addUser(UserRegistrationRequest user);
    UserDTO updateUserById(UserDTO userDTO);// change to ById
    // add deactivate user method .role("admin")
    void deactivateUserById(Integer id);
}
