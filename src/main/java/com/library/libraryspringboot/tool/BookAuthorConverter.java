package com.library.libraryspringboot.tool;

import com.library.libraryspringboot.entity.BookAuthor;
import com.library.libraryspringboot.dto.AuthorDTO;
import com.library.libraryspringboot.dto.BookAuthorDTO;
import com.library.libraryspringboot.dto.BookDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookAuthorConverter {  //@TODO: Remove unnecessary fields !
    private final ModelMapper modelMapper;
    private BookConverter bookConverter;
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

