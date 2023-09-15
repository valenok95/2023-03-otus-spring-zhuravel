package com.example.homework05.dao;

import com.example.homework05.domain.Genre;
import java.util.List;

public interface GenreDao {
    int count();

    void insert(Genre person);

    Genre getById(long id);

    List<Genre> getAll();

    void deleteById(long id);
}
