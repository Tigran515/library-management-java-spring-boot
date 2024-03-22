package com.library.libraryspringboot.service;

import com.library.libraryspringboot.dto.AuthenticationRequest;
import com.library.libraryspringboot.config.security.jwt.JwtUtil;
import com.library.libraryspringboot.dto.UserDTO;
import com.library.libraryspringboot.repository.UserRepository;
import com.library.libraryspringboot.tool.UserConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtTokenUtil;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Value("${JWT_TOKEN_HEADER}")
    protected String JWT_TOKEN_HEADER;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtil jwtTokenUtil,
                                     UserRepository userRepository, UserConverter userConverter) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public ResponseEntity<?> authenticate(AuthenticationRequest authenticationRequest) throws AccessDeniedException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            LOGGER.error("Incorrect username or password. User with [username=" + authenticationRequest.getUsername() + "] was not authenticated");
            throw new AccessDeniedException("Incorrect username or password");
        }
        final UserDetails USER_DETAILS = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        // at the end create an authentication response instance and pass it back creating response
        // entity out of it.This is standard REST API.

        HttpHeaders jwtHeader = getJwtHeader(USER_DETAILS);
        UserDTO loggedInUserDto = userConverter.fromEntityToDto(userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(() -> new NoSuchElementException("User not found")));
        return new ResponseEntity<>(loggedInUserDto, jwtHeader, 200);
    }

    private HttpHeaders getJwtHeader(UserDetails userDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenUtil.generateToken(userDetails)); //@TODO:At the end change JWT_TOKEN_HEADER value in application.properties for safety
        return headers;
    }
}
