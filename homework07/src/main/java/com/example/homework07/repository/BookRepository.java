package com.example.homework07.repository;

import com.example.homework07.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Override
    @EntityGraph(value = "book-entity-graph")
    Optional<Book> findById(Long id);

    @Override
    @EntityGraph(value = "book-entity-graph")
    List<Book> findAll();
}
