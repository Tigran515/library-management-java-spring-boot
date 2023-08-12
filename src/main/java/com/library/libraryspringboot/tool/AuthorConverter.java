package com.library.libraryspringboot.tool;

import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.dto.AuthorDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class AuthorConverter implements Serializable {

    public AuthorDTO fromEntityToDto(Author author) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(author, AuthorDTO.class);
    }

    public Author fromDtoToEntity(AuthorDTO authorDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(authorDTO, Author.class);
    }
}
