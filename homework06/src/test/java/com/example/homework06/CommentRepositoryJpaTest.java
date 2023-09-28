package com.example.homework06;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.homework06.model.Author;
import com.example.homework06.model.Book;
import com.example.homework06.model.Comment;
import com.example.homework06.model.Genre;
import com.example.homework06.repository.CommentRepositoryJpa;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DisplayName("Dao для работы с комментариями")
@DataJpaTest
@Import(CommentRepositoryJpa.class)
public class CommentRepositoryJpaTest {

    private static final Long EXISTING_COMMENT_ID = 1L;

    private static final String EXISTING_COMMENT_TEXT = "I liked it";
    private static final Long EXISTING_BOOK_ID = 2L;
    private static final Book EXISTING_BOOK_2 = new Book(2, "Eugene Onegin", new Author(2,
            "Pushkin"), new Genre(1, "roman"));
    private static final Comment EXISTING_COMMENT_1 = new Comment(1, "I liked it", EXISTING_BOOK_2);
    private static final Comment EXISTING_COMMENT_2 = new Comment(2, "Long long story", EXISTING_BOOK_2);

    @Autowired
    private TestEntityManager em;
    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    @DisplayName("возвращать комментарий по id")
    @Test
    void shouldFindCommentById() {
        Optional<Comment> comment = commentRepositoryJpa.getById(EXISTING_COMMENT_ID);
        assertThat(comment).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("text", EXISTING_COMMENT_TEXT);
    }

    @DisplayName("возвращать комментарий по id")
    @Test
    void shouldFindCommentByBookId() {
        List<Comment> actualComments = commentRepositoryJpa.getAllByBookId(EXISTING_BOOK_ID);
        List<Comment> expectedComments = List.of(EXISTING_COMMENT_1, EXISTING_COMMENT_2);
        assertThat(actualComments).hasSameElementsAs(expectedComments);
    }

    @DisplayName("добавлять комментарий в БД")
    @Test
    void shouldInsertComment() {
        Comment expectedComment = new Comment(0, "Amazing", EXISTING_BOOK_2);
        Comment savedComment = commentRepositoryJpa.insert(expectedComment);

        Comment actualComment = commentRepositoryJpa.getById(savedComment.getId()).get();
        assertThat(actualComment).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedComment);
    }

    @DisplayName("обновлять комментарий в БД")
    @Test
    void shouldUpdateComment() {
        Comment newComment = new Comment(1, "newComment", EXISTING_BOOK_2);
        em.detach((newComment));
        commentRepositoryJpa.update(newComment);

        Comment actualComment = commentRepositoryJpa.getById(EXISTING_COMMENT_ID).get();
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(newComment);
    }

    @DisplayName("удалять комментарий по id")
    @Test
    void shouldDeleteCommentById() {

        Comment comment = em.find(Comment.class, EXISTING_COMMENT_ID);
        assertThat(comment).isNotNull();
        commentRepositoryJpa.deleteById(EXISTING_COMMENT_ID);

        em.clear();
        comment = em.find(Comment.class, EXISTING_COMMENT_ID);
        assertThat(comment).isNull();
    }
}

