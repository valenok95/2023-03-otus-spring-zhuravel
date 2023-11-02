package com.example.homework07.service;

import com.example.homework07.exceptions.NotFoundException;
import com.example.homework07.model.Author;
import com.example.homework07.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public Author getAuthorById(long id) {
        return authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Автор не " +
                "обнаружен!"));
    }

}
