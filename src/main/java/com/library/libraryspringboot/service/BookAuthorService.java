package com.library.libraryspringboot.service;

import com.library.libraryspringboot.Tool.BookAuthorId;
import com.library.libraryspringboot.Tool.SearchCriteria;
import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.entity.BookAuthor;
import dto.BookAuthorDTO;
import org.springframework.data.domain.Page;


public interface BookAuthorService {
    <T> T findBooksAndAuthorsByCriteria(SearchCriteria detail); // advanced search method
    Page<BookAuthorDTO> getAllBooksAndAuthors(final Integer pageNumber, final Integer size);
    BookAuthor addBookAuthor(Book book, Author author); //@TODO change 
}
