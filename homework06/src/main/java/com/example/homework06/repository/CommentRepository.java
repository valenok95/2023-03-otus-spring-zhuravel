package com.example.homework06.repository;

import com.example.homework06.model.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment insert(Comment comment);

    Comment update(Comment comment);

    Optional<Comment> getById(long id);

    List<Comment> getAll();

    void deleteById(long id);

    List<Comment> getAllByBookId(long id);
}