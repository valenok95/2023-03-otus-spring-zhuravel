package com.example.homework05;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.homework05.domain.Author;
import com.example.homework05.domain.Book;
import com.example.homework05.domain.Genre;
import com.example.homework05.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

@SpringBootTest
public class BookServiceTest {
    private static final int EXISTING_BOOK_ID_FOR_GET_TEST = 1;
    private static final int EXISTING_BOOK_ID_FOR_SAVE_DELETE_TEST = 999;
    private static final String EXISTING_BOOK_NAME = "Voyna i Mir";
    private static final Author EXISTING_BOOK_AUTHOR = new Author(1, "Lev Tolstoy");
    private static final Genre EXISTING_BOOK_GENRE = new Genre(1, "roman");
    private static final Book EXPECTED_BOOK = new Book(EXISTING_BOOK_ID_FOR_GET_TEST, EXISTING_BOOK_NAME,
            EXISTING_BOOK_AUTHOR, EXISTING_BOOK_GENRE);
    @Autowired
    BookService bookService;

    @Test
    void getBookTest() {
        Book actualBook = bookService.getBookById(EXISTING_BOOK_ID_FOR_GET_TEST);
        assertThat(actualBook).isEqualTo(EXPECTED_BOOK);
    }

    @Test
    void saveBookTest() {
        Book expectedBook = new Book(999, "Test book",
                EXISTING_BOOK_AUTHOR, EXISTING_BOOK_GENRE);
        bookService.saveBook(expectedBook);
        Book actualBook = bookService.getBookById(999);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    void deleteBookTest() {
        Book expectedBook = new Book(EXISTING_BOOK_ID_FOR_SAVE_DELETE_TEST, "Test book",
                EXISTING_BOOK_AUTHOR, EXISTING_BOOK_GENRE);
        bookService.saveBook(expectedBook);

        assertThatCode(() -> bookService.getBookById(EXISTING_BOOK_ID_FOR_SAVE_DELETE_TEST))
                .doesNotThrowAnyException();

        bookService.deleteById(EXISTING_BOOK_ID_FOR_SAVE_DELETE_TEST);

        assertThatThrownBy(() -> bookService.getBookById(EXISTING_BOOK_ID_FOR_SAVE_DELETE_TEST))
                .isInstanceOf(EmptyResultDataAccessException.class);
        

    }
}
