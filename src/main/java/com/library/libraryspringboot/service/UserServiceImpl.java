package com.library.libraryspringboot.service;

import com.library.libraryspringboot.dto.UserDTO;
import com.library.libraryspringboot.entity.User;
import com.library.libraryspringboot.repository.UserRepository;
import com.library.libraryspringboot.dto.UserInformationUpdateRequest;
import com.library.libraryspringboot.tool.UserConverter;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserConverter userConverter;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public Optional<UserDTO> updateUserInformationByUsername(UserInformationUpdateRequest userInformationUpdateRequest) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(userInformationUpdateRequest.getUsername())
                .orElseThrow(() -> {
                    String errorMsg = "User with [username=" + userInformationUpdateRequest.getUsername() + "] not found";
                    LOGGER.error(errorMsg);
                    return new NoSuchElementException(errorMsg);
                }));
        userOptional.ifPresent(user -> {
            user.setName(userInformationUpdateRequest.getName());
            user.setLname(userInformationUpdateRequest.getLname());
            user.setSname(userInformationUpdateRequest.getSname());
            userRepository.save(user);
            LOGGER.info(MessageFormat.format("Personal information of the user with [username={0}] was updated", userInformationUpdateRequest.getUsername()));
        });
        return userOptional.stream().map(userConverter::fromEntityToDto).findFirst();
    }
}
