package com.example.homework05;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.homework05.dto.BookDto;
import com.example.homework05.dto.BookResponseDto;
import com.example.homework05.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

@SpringBootTest
public class BookServiceTest {
    private static final Long EXISTING_BOOK_ID_FOR_GET_TEST = 1L;
    private static final Long EXISTING_BOOK_ID_FOR_DELETE_TEST = 777L;
    private static final Long EXISTING_BOOK_ID_FOR_SAVE_TEST = 888L;
    private static final String EXISTING_BOOK_NAME = "Voyna i Mir";
    private static final Long EXISTING_BOOK_AUTHOR_ID = 1L;
    private static final Long EXISTING_BOOK_GENRE_ID = 1L;
    private static final String EXISTING_BOOK_AUTHOR_NAME = "Lev Tolstoy";
    private static final String EXISTING_BOOK_GENRE_NAME = "roman";
    private static final BookResponseDto EXPECTED_BOOK_RESPONSE_DTO = new BookResponseDto(EXISTING_BOOK_ID_FOR_GET_TEST,
            EXISTING_BOOK_NAME,
            EXISTING_BOOK_AUTHOR_NAME, EXISTING_BOOK_GENRE_NAME);
    @Autowired
    private BookService bookService;

    @Test
    void getBookTest() {
        BookResponseDto actualBook = bookService.getBookById(EXISTING_BOOK_ID_FOR_GET_TEST);
        assertThat(actualBook).isEqualTo(EXPECTED_BOOK_RESPONSE_DTO);
    }

    @Test
    void saveBookTest() {
        BookDto saveBook = new BookDto(EXISTING_BOOK_ID_FOR_SAVE_TEST, "Test book",
                EXISTING_BOOK_AUTHOR_ID, EXISTING_BOOK_GENRE_ID);
        BookResponseDto expectedBook = new BookResponseDto(EXISTING_BOOK_ID_FOR_SAVE_TEST, "Test book",
                EXISTING_BOOK_AUTHOR_NAME, EXISTING_BOOK_GENRE_NAME);
        bookService.saveBook(saveBook);
        BookResponseDto actualBook = bookService.getBookById(EXISTING_BOOK_ID_FOR_SAVE_TEST);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    void updateBookTest() {
        BookDto saveBook = new BookDto(999L, "Test book",
                EXISTING_BOOK_AUTHOR_ID, EXISTING_BOOK_GENRE_ID);
        BookDto updateBook = new BookDto(999L, "Test book 2",
                EXISTING_BOOK_AUTHOR_ID, EXISTING_BOOK_GENRE_ID);
        BookResponseDto expectedBook = new BookResponseDto(999L, "Test book 2",
                EXISTING_BOOK_AUTHOR_NAME, EXISTING_BOOK_GENRE_NAME);
        bookService.saveBook(saveBook);
        bookService.updateBook(updateBook);
        BookResponseDto actualBook = bookService.getBookById(999);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    void deleteBookTest() {
        BookDto expectedBook = new BookDto(EXISTING_BOOK_ID_FOR_DELETE_TEST, "Test book",
                EXISTING_BOOK_AUTHOR_ID, EXISTING_BOOK_GENRE_ID);
        bookService.saveBook(expectedBook);

        assertThatCode(() -> bookService.getBookById(EXISTING_BOOK_ID_FOR_DELETE_TEST))
                .doesNotThrowAnyException();

        bookService.deleteById(EXISTING_BOOK_ID_FOR_DELETE_TEST);

        assertThatThrownBy(() -> bookService.getBookById(EXISTING_BOOK_ID_FOR_DELETE_TEST))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
