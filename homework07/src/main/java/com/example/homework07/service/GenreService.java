package com.example.homework07.service;

import com.example.homework07.exceptions.NotFoundException;
import com.example.homework07.model.Genre;
import com.example.homework07.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public Genre getGenreById(long id) {
        return genreRepository.findById(id).orElseThrow(() -> new NotFoundException("Жанр не " +
                "обнаружен!"));
    }

}
