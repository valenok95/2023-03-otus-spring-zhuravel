package com.example.homework05.dao;

import com.example.homework05.domain.Author;
import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    int count();

    Author insert(Author author);

    Optional<Author> getById(long id);

    List<Author> getAll();

    void deleteById(long id);
}
