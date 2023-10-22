package com.example.homework07.repository;

import com.example.homework07.model.Book;
import com.example.homework07.model.Genre;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
/*    @Override
    @EntityGraph()
    Optional<Genre> findById(Long id);*/
}
