package com.library.libraryspringboot.Tool;

import com.library.libraryspringboot.entity.Author;
import dto.AuthorDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter {  // shouldn't it implement Serializable ?

    public AuthorDTO fromEntityToDto(Author author) {
        ModelMapper modelMapper = new ModelMapper();
        dto.AuthorDTO authorDTO = modelMapper.map(author, dto.AuthorDTO.class);
        return authorDTO;
    }

    public Author fromDtoToEntity(AuthorDTO authorDTO){
        ModelMapper modelMapper = new ModelMapper();
        Author author = modelMapper.map(authorDTO, Author.class);
        return author;
    }
}