package com.example.homework05;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.homework05.dao.BookDaoJdbc;
import com.example.homework05.domain.Author;
import com.example.homework05.domain.Book;
import com.example.homework05.domain.Genre;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;

@DisplayName("Dao для работы с книгами")
@JdbcTest
@Import(BookDaoJdbc.class)
public class BookDaoJdbcTest {

    private static final int EXPECTED_BOOKS_COUNT = 2;
    private static final int EXISTING_BOOK_ID = 1;
    private static final String EXISTING_BOOK_NAME = "Voyna i Mir";
    private static final Author EXISTING_BOOK_AUTHOR = new Author(1,"Lev Tolstoy");
    private static final Genre EXISTING_BOOK_GENRE = new Genre(1, "roman");

    @Autowired
    private BookDaoJdbc bookDao;

    @DisplayName("возвращать ожидаемое количество книг в БД")
    @Test
    void shouldReturnExpectedBookCount() {
        int actualPersonsCount = bookDao.count();
        assertThat(actualPersonsCount).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        int countBeforeInsert = bookDao.count();
        assertThat(countBeforeInsert).isEqualTo(EXPECTED_BOOKS_COUNT);

        Book expectedBook = new Book(3, "Kapitanskaya dochka", new Author(2,"Pushkin"), new Genre(1,
                "roman"));
        bookDao.insert(expectedBook);

        Book actualBook = bookDao.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("возвращать книгу по id")
    @Test
    void shouldReturnExpectedBookById() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, EXISTING_BOOK_AUTHOR
                , EXISTING_BOOK_GENRE);
        Book actualBook = bookDao.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldCorrectDeleteBookById() {
        int countBeforeDelete = bookDao.count();
        assertThat(countBeforeDelete).isEqualTo(EXPECTED_BOOKS_COUNT);


        assertThatCode(() -> bookDao.getById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        bookDao.deleteById(EXISTING_BOOK_ID);

        assertThatThrownBy(() -> bookDao.getById(EXISTING_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedPersonsList() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, EXISTING_BOOK_AUTHOR
                , EXISTING_BOOK_GENRE);
        List<Book> actualBookList = bookDao.getAll();
        assertThat(actualBookList)
                .contains(expectedBook);
    }
}
