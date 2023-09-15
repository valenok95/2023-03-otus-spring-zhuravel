package com.example.homework05.dao;

import com.example.homework05.domain.Author;
import java.util.List;

public interface AuthorDao {
    int count();

    void insert(Author author);

    Author getById(long id);

    List<Author> getAll();

    void deleteById(long id);
}
