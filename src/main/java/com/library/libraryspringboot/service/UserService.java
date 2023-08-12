package com.library.libraryspringboot.service;

import com.library.libraryspringboot.dto.UserDTO;
import com.library.libraryspringboot.dto.UserInformationUpdateRequest;

import java.util.Optional;

public interface UserService {
    Optional<UserDTO> updateUserInformationByUsername (UserInformationUpdateRequest userInformationUpdateRequest);
}
