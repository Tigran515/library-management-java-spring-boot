package com.library.libraryspringboot.service;

import com.library.libraryspringboot.dto.UserDTO;
import com.library.libraryspringboot.dto.UserRegistrationRequest;
import com.library.libraryspringboot.dto.validation.PutValidation;
import com.library.libraryspringboot.entity.User;
import com.library.libraryspringboot.enums.RoleEnum;
import com.library.libraryspringboot.repository.UserRepository;
import com.library.libraryspringboot.tool.BeanCopyUtils;
import com.library.libraryspringboot.tool.UserConverter;
import com.library.libraryspringboot.tool.UserRegistrationRequestConverter;
import com.library.libraryspringboot.tool.ValidationTool;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final UserRegistrationRequestConverter userRegistrationRequestConverter;
    private final BeanCopyUtils beanCopyUtils;
    private final ValidationTool validationTool;
    private final PasswordEncoder passwordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter, UserRegistrationRequestConverter userRegistrationRequestConverter, BeanCopyUtils beanCopyUtils, ValidationTool validationTool, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.userRegistrationRequestConverter = userRegistrationRequestConverter;
        this.beanCopyUtils = beanCopyUtils;
        this.validationTool = validationTool;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<UserDTO> getUsers(Integer offset, Integer limit) {
        Page<User> users = userRepository.findAll(PageRequest.of(offset, limit));
        LOGGER.info("[count=" + users.getSize() + "] users were found");
        return users.map(userConverter::fromEntityToDto);
    }

    @Override
    public UserDTO getUserById(@NotNull Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMsg = "User with [ID=" + id + "] was not found";
                    LOGGER.error(errorMsg);
                    return new NoSuchElementException(errorMsg);
                });
        LOGGER.info("User with [id=" + id + "] was found");
        return userConverter.fromEntityToDto(user);
    }

    @Override
    public UserDTO addUser(UserRegistrationRequest userRegistrationRequest) {
        validationTool.validate(userRegistrationRequest);
        if (userRepository.existsUserByUsername(userRegistrationRequest.getUsername())) {
            String errorMsg = "User with [username=" + userRegistrationRequest + "] already exists";
            LOGGER.error(errorMsg);
            throw new DuplicateKeyException(errorMsg);
        }
        User newUser = userRegistrationRequestConverter.fromDtoToEntity(userRegistrationRequest);
        newUser.setActive(true);
        newUser.setRole(RoleEnum.user);
        newUser.setCreated(new Timestamp(System.currentTimeMillis()));
        newUser.setPassword(passwordEncoder.encode(userRegistrationRequest.getPassword()));
        userRepository.save(newUser);
        LOGGER.info("User was saved");
        return userConverter.fromEntityToDto(newUser);
    }

    @Override //@TODO:🔥 Angular!! also make changes in the Client code
    public UserDTO updateUserById(UserDTO userUpdateRequest) { // can be replaced with UserDto
        //id is empty == bad request
        if (userUpdateRequest.getId() == null) { //✅
            String errorMsg = "Cannot update the User [ID=null].";
            LOGGER.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        validationTool.validateByGroup(userUpdateRequest, PutValidation.class);
        User existingUser = userRepository.findById(userUpdateRequest.getId())
                .orElseThrow(() -> {
                    String errorMsg = "User with [ID="+userUpdateRequest.getId()+"] was not found";
                    LOGGER.error(errorMsg);
                    return new NoSuchElementException(errorMsg); //✅
                });
        if (existingUser == null) {
            LOGGER.warn("User with [ID=" + userUpdateRequest.getId() + "] not found");
        } else {
            beanCopyUtils.copyNonNullProperties(userUpdateRequest, existingUser);
            userRepository.save(existingUser);
            LOGGER.info(MessageFormat.format("User with [ID={0}] was updated", existingUser.getId()));
        }
        return userConverter.fromEntityToDto(existingUser);
    }

    @Override
    public void deactivateUserById(@NotNull Integer id) { //.role("admin)
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            LOGGER.error(MessageFormat.format("Cannot deactivate user with non existing [ID={0}]", id));
            return;
        }
        user.get().setActive(false);
        userRepository.save(user.get());
        LOGGER.info("User with [id=" + id + "] was deactivated");
    }
}
