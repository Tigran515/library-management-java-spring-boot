package com.library.libraryspringboot.Tool;

import com.library.libraryspringboot.entity.Book;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookConverter {  // shouldn't it implement Serializable ?

    public dto.BookDTO fromEntityToDto(Book book) {
        ModelMapper modelMapper = new ModelMapper();
        dto.BookDTO bookDTO = modelMapper.map(book, dto.BookDTO.class);
        return bookDTO;
    }

    public Book fromDtoToEntity(dto.BookDTO bookDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Book book = modelMapper.map(bookDTO, Book.class);
        return book;
    }

}