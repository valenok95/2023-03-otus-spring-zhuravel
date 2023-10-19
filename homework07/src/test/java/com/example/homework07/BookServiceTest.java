package com.example.homework07;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.homework07.dto.BookDto;
import com.example.homework07.dto.BookResponseDto;
import com.example.homework07.exceptions.NotFoundException;
import com.example.homework07.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
public class BookServiceTest {
    private static final Long EXISTING_BOOK_ID_FOR_GET_TEST = 1L;
    private static final Long EXISTING_BOOK_ID_WITH_COMMENTS = 2L;
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
        BookDto saveBook = new BookDto(null, "Test book",
                EXISTING_BOOK_AUTHOR_ID, EXISTING_BOOK_GENRE_ID);
        BookResponseDto expectedBook = new BookResponseDto(null, "Test book",
                EXISTING_BOOK_AUTHOR_NAME, EXISTING_BOOK_GENRE_NAME);
        var id = bookService.saveBook(saveBook).getId();
        BookResponseDto actualBook = bookService.getBookById(id);
        assertThat(actualBook).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedBook);
    }

    @Test
    void deleteBookTest() {
        BookDto expectedBook = new BookDto(null, "Test book",
                EXISTING_BOOK_AUTHOR_ID, EXISTING_BOOK_GENRE_ID);
        var response = bookService.saveBook(expectedBook);
        long newId = response.getId();
        assertThatCode(() -> bookService.getBookById(newId))
                .doesNotThrowAnyException();

        bookService.deleteById(newId);

        assertThatThrownBy(() -> bookService.getBookById(newId))
                .isInstanceOf(NotFoundException.class);
    }
    @Test
    void deleteBookWithCommentsTest() {
        assertThatCode(() -> bookService.getBookById(EXISTING_BOOK_ID_WITH_COMMENTS))
                .doesNotThrowAnyException();

        assertThatThrownBy(() -> bookService.deleteById(EXISTING_BOOK_ID_WITH_COMMENTS))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void updateBookTest() {
        BookDto saveBookDto = new BookDto(null, "Test book",
                EXISTING_BOOK_AUTHOR_ID, EXISTING_BOOK_GENRE_ID);
        var response = bookService.saveBook(saveBookDto);
        long newId = response.getId();
        BookDto updateBookDto = new BookDto(newId, "Test book 2",
                EXISTING_BOOK_AUTHOR_ID, EXISTING_BOOK_GENRE_ID);
        BookResponseDto expectedBookResponse = new BookResponseDto(newId, "Test book 2",
                EXISTING_BOOK_AUTHOR_NAME, EXISTING_BOOK_GENRE_NAME);
        bookService.saveBook(saveBookDto);
        bookService.updateBook(updateBookDto);
        BookResponseDto actualBook = bookService.getBookById(newId);
        assertThat(actualBook).isEqualTo(expectedBookResponse);
    }
}



   
