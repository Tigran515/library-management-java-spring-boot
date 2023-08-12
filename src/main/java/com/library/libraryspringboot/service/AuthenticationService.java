package com.library.libraryspringboot.service;

import com.library.libraryspringboot.dto.AuthenticationRequest;
import org.springframework.http.ResponseEntity;

import java.nio.file.AccessDeniedException;

public interface AuthenticationService {
    ResponseEntity<?> authenticate(AuthenticationRequest authenticationRequest) throws AccessDeniedException;
    //@TODO: add here or create UserService ? to add the followings register(RegisterRequest request), deactivate(String userId) methods
}
