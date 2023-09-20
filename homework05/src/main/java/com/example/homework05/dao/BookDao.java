package com.example.homework05.dao;

import com.example.homework05.domain.Book;
import java.util.List;
import java.util.Optional;

public interface BookDao {
    int count();

    Book insert(Book book);

    Book update(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    void deleteById(long id);
}
