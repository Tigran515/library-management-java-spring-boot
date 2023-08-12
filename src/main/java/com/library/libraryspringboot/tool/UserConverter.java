package com.library.libraryspringboot.tool;

import com.library.libraryspringboot.dto.UserDTO;
import com.library.libraryspringboot.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class UserConverter implements Serializable {

    public UserDTO fromEntityToDto(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDTO.class);
    }
}
