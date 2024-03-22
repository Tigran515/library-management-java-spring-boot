package com.library.libraryspringboot.tool;

import com.library.libraryspringboot.dto.UserRegistrationRequest;
import com.library.libraryspringboot.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class UserRegistrationRequestConverter implements Serializable {

    public User fromDtoToEntity(UserRegistrationRequest userRegistrationRequest) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userRegistrationRequest, User.class);
    }
}
