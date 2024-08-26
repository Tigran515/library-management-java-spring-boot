package com.library.libraryspringboot.controller;

import com.library.libraryspringboot.dto.UserDTO;
import com.library.libraryspringboot.dto.UserRegistrationRequest;
import com.library.libraryspringboot.dto.validation.PutValidation;
import com.library.libraryspringboot.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Get users", description = "Get a list of users (paginated)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the users")
    })
    @GetMapping
    Page<UserDTO> getUsers(
            @RequestParam(defaultValue = "0") final Integer offset,
            @RequestParam(defaultValue = "200") final Integer limit
    ) {
        LOGGER.info("Incoming HTTP GET request to [URL={}]", HTTP_SERVLET_REQUEST.getRequestURL().toString());
        return userService.getUsers(offset, limit);
    }

    @Operation(summary = "Get user by ID", description = "Get an user by it's ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the user by ID")
    })
    @GetMapping("/user/{id}")
    UserDTO getUserById(@PathVariable @NotNull @Positive(message = "user ID should be positive") Integer id) {
        LOGGER.info("Incoming HTTP GET request to [URL={}]", HTTP_SERVLET_REQUEST.getRequestURL().toString());
        return userService.getUserById(id);
    }

    @Operation(summary = "Add an user", description = "Add a new user data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "new user data has been added")
    })
    @PostMapping("/post")
    UserDTO post(@RequestBody @Valid UserRegistrationRequest user) {
        LOGGER.info("Incoming HTTP GET request to [URL={}]", HTTP_SERVLET_REQUEST.getRequestURL().toString());
        return userService.addUser(user);
    }

    @Operation(summary = "Update an user", description = "Update an existing user's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user data has been updated")
    })
    @PutMapping("/update")
    UserDTO put(@RequestBody @Validated(PutValidation.class) UserDTO userFieldsUpdateRequest) {
        LOGGER.info("Incoming HTTP POST request to [URL={}]", HTTP_SERVLET_REQUEST.getRequestURL().toString());
        return userService.updateUserById(userFieldsUpdateRequest);
    }

    @Operation(summary = "Deactivate an user", description = "Deactivate an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user has been deactivated")
    })
    @PatchMapping("/{id}/deactivate")// Does this follow the REST API conventions?
    void deactivateById(@PathVariable @NotNull Integer id) {
        LOGGER.info("Incoming HTTP DELETE request to [URL={}]", HTTP_SERVLET_REQUEST.getRequestURL().toString());
        userService.deactivateUserById(id);
    }
}
