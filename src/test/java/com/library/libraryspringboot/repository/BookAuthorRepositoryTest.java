//package com.library.libraryspringboot.repository;
//
//import com.library.libraryspringboot.entity.Book;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@DataJpaTest
//class BookAuthorRepositoryTest {
//
//    @Autowired
//    private  BookAuthorRepository bookAuthorRepositoryunderTest;
//    @Autowired
//    private  BookRepository bookRepositoryUnderTest;
//
//
//    @Test
//    void itShouldFindAuthorIdsByBookId() {
//        //given
//        Integer bookId = 1;
//        Optional<Book> book = bookRepositoryUnderTest.findById(String.valueOf(bookId));
//        //when
//        List<Integer> authorIds = bookAuthorRepositoryunderTest.findAuthorIdsByBookId(book.get());
//        //then
//        assertNotNull(authorIds);
//        assertFalse(authorIds.isEmpty()); // Check that the list is not empty
//
//    }
//}
