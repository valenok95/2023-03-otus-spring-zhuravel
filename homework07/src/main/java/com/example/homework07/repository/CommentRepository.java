package com.example.homework07.repository;

import com.example.homework07.model.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(value = "comment-entity-graph")
    List<Comment> findAllByBookId(long id);
    
    @Override
    @EntityGraph(value = "comment-entity-graph")
    Optional<Comment> findById(Long id);
}