package com.example.homework07;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.homework07.model.Author;
import com.example.homework07.model.Book;
import com.example.homework07.model.Genre;
import com.example.homework07.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Тест JPA для работы с книгами")
public class BookRepositoryJpaTest {

    private static final Long EXISTING_BOOK_ID = 1L;
    private static final Long EXISTING_BOOK_ID_2 = 2L;
    private static final String EXISTING_BOOK_NAME_2 = "Kapitanskaya dochka 2";
    private static final Author EXISTING_BOOK_AUTHOR_2 = new Author(2, "Pushkin");
    private static final Genre EXISTING_BOOK_GENRE_2 = new Genre(1, "roman");
    @Autowired
    private BookRepository bookRepository;

    @DisplayName("возвращать книгу по id")
    @Test
    void shouldFindBookById() {
        Optional<Book> book = bookRepository.findById(EXISTING_BOOK_ID_2);
        assertThat(book).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("name", EXISTING_BOOK_NAME_2);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        int countBeforeInsert = bookRepository.findAll().size();

        Book expectedBook = new Book(null, "Kapitanskaya dochka", new Author(2, "Pushkin"),
                new Genre(1,
                        "roman"));
        Book savedBook = bookRepository.save(expectedBook);

        int countAfterInsert = bookRepository.findAll().size();
        assertThat(countAfterInsert).isEqualTo(countBeforeInsert + 1);

        Book actualBook = bookRepository.findById(savedBook.getId()).get();
        assertThat(actualBook).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedBook);
    }

    @DisplayName("обновить книгу в БД")
    @Test
    void shouldUpdateBook() {

        Book updatedBook = new Book(EXISTING_BOOK_ID_2, "Kapitanskaya dochka 2", new Author(2,
                "Pushkin"),
                new Genre(1,
                        "roman"));
        bookRepository.save(updatedBook);

        Book actualBook = bookRepository.findById(EXISTING_BOOK_ID_2).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(updatedBook);
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldDeleteBookById() {

        Book book = bookRepository.findById(EXISTING_BOOK_ID).get();
        assertThat(book).isNotNull();
        bookRepository.deleteById(EXISTING_BOOK_ID);

        book = bookRepository.findById(EXISTING_BOOK_ID).orElse(null);
        assertThat(book).isNull();
    }


    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedBookList() {
        Book expectedBook = new Book(EXISTING_BOOK_ID_2, EXISTING_BOOK_NAME_2,
                EXISTING_BOOK_AUTHOR_2
                , EXISTING_BOOK_GENRE_2);
        List<Book> actualBookList = bookRepository.findAll();
        assertThat(actualBookList)
                .contains(expectedBook);
    }
}
