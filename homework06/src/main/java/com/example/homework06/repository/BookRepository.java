package com.example.homework06.repository;

import com.example.homework06.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Book insert(Book book);

    Book update(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    void deleteById(long id);
}
