package com.library.libraryspringboot.controller;

import com.library.libraryspringboot.dto.UserDTO;
import com.library.libraryspringboot.service.UserServiceImpl;
import com.library.libraryspringboot.dto.UserInformationUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserRestController {
    private final UserServiceImpl userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);
    private final HttpServletRequest HTTP_SERVLET_REQUEST;

    public UserRestController(UserServiceImpl userService, HttpServletRequest HTTP_SERVLET_REQUEST) {
        this.userService = userService;
        this.HTTP_SERVLET_REQUEST = HTTP_SERVLET_REQUEST;
    }

    @Operation(summary = "Update an user", description = "Update an existing user's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user data has been updated")
    })
    @PatchMapping("/update")
    public Optional<UserDTO> patch(@RequestBody UserInformationUpdateRequest userInformationUpdateRequest) {
        LOGGER.info("Incoming HTTP POST request to [URL={}]", HTTP_SERVLET_REQUEST.getRequestURL().toString());
        return userService.updateUserInformationByUsername(userInformationUpdateRequest);
    }
}
