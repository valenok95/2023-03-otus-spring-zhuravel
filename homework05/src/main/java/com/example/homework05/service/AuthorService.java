package com.example.homework05.service;

import com.example.homework05.dao.AuthorDao;
import com.example.homework05.domain.Author;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorDao authorDao;

    public void saveAuthor(Author author) {
        authorDao.insert(author);
    }

    public Author getAuthorById(long id) {
        return authorDao.getById(id).orElseThrow();
    }

    public List<Author> getAll() {
        return authorDao.getAll();
    }
}
