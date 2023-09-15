package com.example.homework05.service;

import com.example.homework05.dao.GenreDao;
import com.example.homework05.domain.Genre;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreDao genreDao;

    public void saveGenre(Genre genre) {
        genreDao.insert(genre);
    }

    public Genre getGenreById(long id) {
        return genreDao.getById(id);
    }

    public List<Genre> getAll() {
        return genreDao.getAll();
    }
}
