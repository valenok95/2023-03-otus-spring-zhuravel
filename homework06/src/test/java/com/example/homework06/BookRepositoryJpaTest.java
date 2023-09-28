package com.example.homework06;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.homework06.model.Author;
import com.example.homework06.model.Book;
import com.example.homework06.model.Genre;
import com.example.homework06.repository.BookRepositoryJpa;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DisplayName("Dao для работы с книгами")
@DataJpaTest
@Import(BookRepositoryJpa.class)
public class BookRepositoryJpaTest {

    private static final int EXPECTED_BOOKS_COUNT = 2;
    private static final Long EXISTING_BOOK_ID = 1L;
    private static final Long EXISTING_BOOK_ID_2 = 2L;
    private static final String EXISTING_BOOK_NAME = "Voyna i Mir";
    private static final Author EXISTING_BOOK_AUTHOR = new Author(1, "Lev Tolstoy");
    private static final Genre EXISTING_BOOK_GENRE = new Genre(1, "roman");
    @Autowired
    private TestEntityManager em;
    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @DisplayName("возвращать книгу по id")
    @Test
    void shouldFindBookById() {
        Optional<Book> book = bookRepositoryJpa.getById(EXISTING_BOOK_ID);
        assertThat(book).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("name", EXISTING_BOOK_NAME);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        int countBeforeInsert = bookRepositoryJpa.getAll().size();
        assertThat(countBeforeInsert).isEqualTo(EXPECTED_BOOKS_COUNT);

        Book expectedBook = new Book(0, "Kapitanskaya dochka", new Author(2, "Pushkin"),
                new Genre(1,
                        "roman"));
        Book savedBook = bookRepositoryJpa.insert(expectedBook);

        Book actualBook = bookRepositoryJpa.getById(savedBook.getId()).get();
        assertThat(actualBook).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedBook);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldUpdateBook() {

        Book updatedBook = new Book(EXISTING_BOOK_ID_2, "Kapitanskaya dochka 2", new Author(2, 
                "Pushkin"),
                new Genre(1,
                        "roman"));
        em.detach((updatedBook));
        bookRepositoryJpa.update(updatedBook);

        Book actualBook = bookRepositoryJpa.getById(EXISTING_BOOK_ID_2).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(updatedBook);
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldDeleteBookById() {

        Book book = em.find(Book.class, EXISTING_BOOK_ID);
        assertThat(book).isNotNull();
        bookRepositoryJpa.deleteById(EXISTING_BOOK_ID);

        em.clear();
        book = em.find(Book.class, EXISTING_BOOK_ID);
        assertThat(book).isNull();
    }
    

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedBookList() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, EXISTING_BOOK_AUTHOR
                , EXISTING_BOOK_GENRE);
        List<Book> actualBookList = bookRepositoryJpa.getAll();
        assertThat(actualBookList)
                .contains(expectedBook);
    }
}
