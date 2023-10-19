package com.example.homework07.service;

import com.example.homework07.dto.CommentDto;
import com.example.homework07.dto.CommentResponseDto;
import com.example.homework07.exceptions.NotFoundException;
import com.example.homework07.model.Book;
import com.example.homework07.model.Comment;
import com.example.homework07.repository.BookRepository;
import com.example.homework07.repository.CommentRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Transactional
    public CommentResponseDto saveComment(CommentDto newComment) {
        Book book = bookRepository.findById(newComment.getBookId()).orElseThrow();
        Comment comment = new Comment(newComment.getText(), book);
        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment.getId(), savedComment.getText(),
                savedComment.getBook().getName(), savedComment.getBook().getAuthor().getName());
    }

    @Transactional
    public void updateComment(CommentDto updateComment) {
        commentRepository.findById(updateComment.getId()).orElseThrow(() -> new NotFoundException(
                "Комментарий для обновления не обнаружен!"));
        Book book =
                bookRepository.findById(updateComment.getBookId()).orElseThrow(() -> new NotFoundException(
                        "Книга не обнаружена!"));
        Comment comment = new Comment(updateComment.getId(), updateComment.getText(), book);
        commentRepository.save(comment);
    }

    @Transactional
    public CommentResponseDto getCommentById(long id) {
        Comment resultComment =
                commentRepository.findById(id).orElseThrow(() -> new NotFoundException(
                        "Комментарий не обнаружен!"));

        return new CommentResponseDto(resultComment.getId(), resultComment.getText(),
                resultComment.getBook().getName(), resultComment.getBook().getAuthor().getName());
    }

    public List<CommentResponseDto> getBookCommentsByBookId(long id) {
        List<Comment> commentList = commentRepository.findAllByBookId(id);
        return commentList.stream().map(comment -> new CommentResponseDto(comment.getId(), comment.getText(),
                comment.getBook().getName(), comment.getBook().getAuthor().getName())).collect(Collectors.toList());
    }


    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }
}
