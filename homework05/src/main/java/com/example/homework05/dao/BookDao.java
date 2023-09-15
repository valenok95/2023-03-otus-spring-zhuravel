package com.example.homework05.dao;

import com.example.homework05.domain.Book;
import java.util.List;

public interface BookDao {
    int count();

    void insert(Book book);

    Book getById(long id);

    List<Book> getAll();

    void deleteById(long id);
}
