package com.library.libraryspringboot.controller;

import com.library.libraryspringboot.dto.AuthenticationRequest;
import com.library.libraryspringboot.repository.AuthorRepository;
import com.library.libraryspringboot.repository.UserRepository;
import com.library.libraryspringboot.service.AuthenticationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping(value = "/auth")  //@TODO: rename
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    private final HttpServletRequest HTTP_SERVLET_REQUEST;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;

    public AuthenticationController(AuthenticationServiceImpl authenticationService, HttpServletRequest HTTP_SERVLET_REQUEST,
                                    UserRepository userRepository,
                                    AuthorRepository authorRepository) {
        this.authenticationService = authenticationService;
        this.HTTP_SERVLET_REQUEST = HTTP_SERVLET_REQUEST;
        this.userRepository = userRepository;
        this.authorRepository = authorRepository;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws AccessDeniedException {
        LOGGER.info("Incoming HTTP POST request to [URL={}]", HTTP_SERVLET_REQUEST.getRequestURL().toString());
        return authenticationService.authenticate(authenticationRequest);
    }


}
