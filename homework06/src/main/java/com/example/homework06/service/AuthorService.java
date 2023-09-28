package com.example.homework06.service;

import com.example.homework06.model.Author;
import com.example.homework06.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public Author getAuthorById(long id) {
        return authorRepository.getById(id).orElseThrow();
    }

}
