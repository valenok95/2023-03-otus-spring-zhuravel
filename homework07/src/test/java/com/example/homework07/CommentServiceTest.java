package com.example.homework07;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.homework07.dto.CommentDto;
import com.example.homework07.dto.CommentResponseDto;
import com.example.homework07.exceptions.NotFoundException;
import com.example.homework07.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentServiceTest {
    private static final Long EXISTING_COMMENT_ID_FOR_GET_TEST = 2L;
    private static final String EXISTING_COMMENT_TEXT = "Long long story";
    private static final String EXISTING_BOOK_NAME_2 = "Kapitanskaya dochka 2";
    private static final Long EXISTING_BOOK_ID = 2L;
    private static final Long EXISTING_BOOK_GENRE_ID = 1L;
    private static final String EXISTING_BOOK_AUTHOR_NAME = "Pushkin";
    private static final String EXISTING_BOOK_GENRE_NAME = "roman";
    private static final CommentResponseDto EXPECTED_COMMENT_RESPONSE_DTO = new CommentResponseDto(EXISTING_COMMENT_ID_FOR_GET_TEST,
            EXISTING_COMMENT_TEXT,
            EXISTING_BOOK_NAME_2, EXISTING_BOOK_AUTHOR_NAME);
    @Autowired
    private CommentService commentService;

    @Test
    void getCommentTest() {
        CommentResponseDto actualComment =
                commentService.getCommentById(EXISTING_COMMENT_ID_FOR_GET_TEST);
        assertThat(actualComment).isEqualTo(EXPECTED_COMMENT_RESPONSE_DTO);
    }

    @Test
    void saveCommentTest() {
        CommentDto saveComment = new CommentDto(null, "Test comment",
                EXISTING_BOOK_ID);
        CommentResponseDto expectedComment = new CommentResponseDto(null, "Test comment",
                EXISTING_BOOK_NAME_2, EXISTING_BOOK_AUTHOR_NAME);
        var id = commentService.saveComment(saveComment).getId();
        CommentResponseDto actualBook = commentService.getCommentById(id);
        assertThat(actualBook).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedComment);
    }

    @Test
    void updateCommentTest() {
        CommentDto saveComment = new CommentDto(null, "Test comment",
                EXISTING_BOOK_ID);
        var response = commentService.saveComment(saveComment);
        long newId = response.getId();
        CommentDto updateCommentDto = new CommentDto(newId, "update Test comment",
                EXISTING_BOOK_ID);
        CommentResponseDto expectedCommentResponse = new CommentResponseDto(newId, "update Test comment",
                EXISTING_BOOK_NAME_2, EXISTING_BOOK_AUTHOR_NAME);
        commentService.saveComment(saveComment);
        commentService.updateComment(updateCommentDto);
        CommentResponseDto actualComment = commentService.getCommentById(newId);
        assertThat(actualComment).isEqualTo(expectedCommentResponse);
    }

    @Test
    void deleteCommentTest() {
        CommentDto expectedComment = new CommentDto(null, "Test comment",
                EXISTING_BOOK_ID);
        var response = commentService.saveComment(expectedComment);
        long newId = response.getId();
        assertThatCode(() -> commentService.getCommentById(newId))
                .doesNotThrowAnyException();

        commentService.deleteById(newId);

        assertThatThrownBy(() -> commentService.getCommentById(newId))
                .isInstanceOf(NotFoundException.class);
    }


}
