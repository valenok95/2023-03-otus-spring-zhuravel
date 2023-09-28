package com.example.homework06.service;

import com.example.homework06.model.Genre;
import com.example.homework06.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public Genre getGenreById(long id) {
        return genreRepository.getById(id).orElseThrow();
    }

}
