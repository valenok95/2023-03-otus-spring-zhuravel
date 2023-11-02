package com.example.homework07;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.homework07.model.Author;
import com.example.homework07.model.Book;
import com.example.homework07.model.Comment;
import com.example.homework07.model.Genre;
import com.example.homework07.repository.CommentRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("Тест JPA для работы с комментариями")
@SpringBootTest
public class CommentRepositoryJpaTest {

    private static final Long EXISTING_COMMENT_ID = 1L;
    private static final Long EXISTING_COMMENT_ID_2 = 2L;

    private static final String EXISTING_COMMENT_TEXT = "Long long story";
    private static final Long EXISTING_BOOK_ID = 2L;
    private static final Book EXISTING_BOOK_2 = new Book(2L, "Kapitanskaya dochka 2", new Author(2,
            "Pushkin"), new Genre(1, "roman"));
    private static final Book EXISTING_BOOK_1 = new Book(2L, "Eugene Onegin", new Author(2,
            "Pushkin"), new Genre(1, "roman"));
    private static final Comment EXISTING_COMMENT_1 = new Comment(1, "newComment", EXISTING_BOOK_2);
    private static final Comment EXISTING_COMMENT_2 = new Comment(2, "Long long story", EXISTING_BOOK_2);


    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("возвращать комментарий по id")
    @Test
    void shouldFindCommentById() {
        Optional<Comment> comment = commentRepository.findById(EXISTING_COMMENT_ID_2);
        assertThat(comment).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("text", EXISTING_COMMENT_TEXT);
    }

    @DisplayName("возвращать комментарий по книжному id")
    @Test
    void shouldFindCommentByBookId() {
        List<Comment> actualComments = commentRepository.findAllByBookId(EXISTING_BOOK_ID);
        List<Comment> expectedComments = List.of(EXISTING_COMMENT_1, EXISTING_COMMENT_2);
        assertThat(actualComments).containsAll(expectedComments);
    }

    @DisplayName("добавлять комментарий в БД")
    @Test
    void shouldInsertComment() {
        Comment expectedComment = new Comment(0, "Amazing", EXISTING_BOOK_2);
        Comment savedComment = commentRepository.save(expectedComment);

        Comment actualComment = commentRepository.findById(savedComment.getId()).get();
        assertThat(actualComment).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedComment);
    }

    @DisplayName("обновлять комментарий в БД")
    @Test
    void shouldUpdateComment() {
        Comment newComment = new Comment(1, "newComment", EXISTING_BOOK_2);
        commentRepository.save(newComment);

        Comment actualComment = commentRepository.findById(EXISTING_COMMENT_ID).get();
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(newComment);
    }

    @DisplayName("удалять комментарий по id")
    @Test
    void shouldDeleteCommentById() {

        Comment comment = commentRepository.findById(EXISTING_COMMENT_ID).orElse(null);
        assertThat(comment).isNotNull();
        commentRepository.deleteById(EXISTING_COMMENT_ID);

        comment = commentRepository.findById(EXISTING_COMMENT_ID).orElse(null);
        assertThat(comment).isNull();
    }
}

