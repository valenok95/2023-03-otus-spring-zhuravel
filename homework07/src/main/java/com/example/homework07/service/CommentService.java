package com.example.homework07.service;

import com.example.homework07.dto.CommentDto;
import com.example.homework07.dto.CommentResponseDto;
import com.example.homework07.exceptions.NotFoundException;
import com.example.homework07.mapper.CommentMapper;
import com.example.homework07.model.Book;
import com.example.homework07.model.Comment;
import com.example.homework07.repository.BookRepository;
import com.example.homework07.repository.CommentRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Transactional
    public CommentResponseDto saveComment(CommentDto newComment) {
        Book book =
                bookRepository.findById(newComment.getBookId()).orElseThrow(() -> new NotFoundException("Книга не " +
                        "обнаружена!"));
        Comment comment = new Comment(newComment.getText(), book);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toResponseDto(savedComment);
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

    @Transactional(readOnly = true)
    public CommentResponseDto getCommentById(long id) {
        Comment resultComment =
                commentRepository.findById(id).orElseThrow(() -> new NotFoundException(
                        "Комментарий не обнаружен!"));

        return commentMapper.toResponseDto(resultComment);
    }
    
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getBookCommentsByBookId(long id) {
        List<Comment> commentList = commentRepository.findAllByBookId(id);
        return commentList.stream().map(commentMapper::toResponseDto).collect(Collectors.toList());
    }


    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }
}
