package com.example.homework06.repository;

import com.example.homework06.model.Author;
import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Optional<Author> getById(long id);
}
