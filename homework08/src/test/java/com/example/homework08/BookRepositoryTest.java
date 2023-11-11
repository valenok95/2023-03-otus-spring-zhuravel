package com.example.homework08;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.homework08.model.Book;
import com.example.homework08.repository.BookRepository;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Тест mongoRep для работы с книгами")
public class BookRepositoryTest extends ContainerBaseTest {

    private static final String EXISTING_BOOK_ID = "test_id";
    private static final String EXISTING_BOOK_NAME = "Eugene Onegin";
    @Autowired
    private BookRepository bookRepository;

    @DisplayName("возвращать книгу по id")
    @Test
    @Order(1)
    void shouldFindBookById() {
        DBObject author = BasicDBObjectBuilder.start()
                .add("name", "Pushkin")
                .get();
        DBObject genre = BasicDBObjectBuilder.start()
                .add("name", "value")
                .get();
        DBObject bookToSave = BasicDBObjectBuilder.start()
                .add("_id", "test_id")
                .add("name", "Eugene Onegin")
                .add("author", author)
                .add("genre", genre)
                .add("clazz", "com.example.homework08.model.Book")
                .get();
        //mongoTemplate.save(bookToSave, "books");
        Optional<Book> book = bookRepository.findById(EXISTING_BOOK_ID);

        assertThat(book).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("name", EXISTING_BOOK_NAME);
    }

/*    @DisplayName("добавлять книгу в БД")
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
    }*/
/*
  

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
    }*/
}
