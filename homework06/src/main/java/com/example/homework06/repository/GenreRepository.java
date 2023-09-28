package com.example.homework06.repository;

import com.example.homework06.model.Genre;
import java.util.Optional;

public interface GenreRepository {
    Optional<Genre> getById(long id);
}
