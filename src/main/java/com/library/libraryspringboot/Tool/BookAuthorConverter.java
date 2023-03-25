package com.library.libraryspringboot.Tool;

import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.entity.BookAuthor;
import dto.AuthorDTO;
import dto.BookAuthorDTO;
import dto.BookDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookAuthorConverter {

    private final ModelMapper modelMapper;

    @Autowired
    private BookConverter bookConverter;

    @Autowired
    private AuthorConverter authorConverter;

    public BookAuthorConverter() {
        this.modelMapper = new ModelMapper();
    }

    public BookAuthorDTO fromEntityToDto(BookAuthor bookAuthor) {
        BookDTO bookDTO = new BookDTO(bookAuthor.getBookAuthorId().getBookId());
        AuthorDTO authorDTO = new AuthorDTO(bookAuthor.getBookAuthorId().getAuthorId());
        return new BookAuthorDTO(bookDTO, authorDTO);
    }

//    public BookAuthor fromDtoToEntity(BookAuthorDTO bookAuthorDTO) {
//        BookAuthor bookAuthor = modelMapper.map(bookAuthorDTO, BookAuthor.class);
//        Book book = bookAuthorDTO.getBookDTO() != null ? modelMapper.map(bookAuthorDTO.getBookDTO(), Book.class) : new Book();
//        Author author = bookAuthorDTO.getAuthorDTO() != null ? modelMapper.map(bookAuthorDTO.getAuthorDTO(), Author.class) : new Author();
//        bookAuthor.setBookAuthorId(new BookAuthorId(book, author)); //create
//        return bookAuthor;
//    }

}

