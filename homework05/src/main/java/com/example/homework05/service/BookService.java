package com.example.homework05.service;

import com.example.homework05.dao.BookDao;
import com.example.homework05.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookDao bookDao;

    public void saveBook(Book book) {
        bookDao.insert(book);
    }

    public Book getBookById(long id) {
        return bookDao.getById(id);
    }

    public void deleteById(long id) {
         bookDao.deleteById(id);
    }
}
