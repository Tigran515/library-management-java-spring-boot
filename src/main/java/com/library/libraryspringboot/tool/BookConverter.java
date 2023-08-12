package com.library.libraryspringboot.tool;

import com.library.libraryspringboot.dto.BookDTO;
import com.library.libraryspringboot.entity.Book;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class BookConverter implements Serializable {  // shouldn't it implement Serializable ?

    public BookDTO fromEntityToDto(Book book) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(book, BookDTO.class);
    }

    public Book fromDtoToEntity(BookDTO bookDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(bookDTO, Book.class);
    }

}
