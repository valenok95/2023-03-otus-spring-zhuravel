package com.example.homework05.dao;

import com.example.homework05.domain.Genre;
import java.util.List;
import java.util.Optional;

public interface GenreDao {
    int count();

    Genre insert(Genre person);

    Optional<Genre> getById(long id);

    List<Genre> getAll();

    void deleteById(long id);
}
