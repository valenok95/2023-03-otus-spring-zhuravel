package com.example.homework05.service;

import com.example.homework05.dao.AuthorDao;
import com.example.homework05.dao.BookDao;
import com.example.homework05.dao.GenreDao;
import com.example.homework05.domain.Author;
import com.example.homework05.domain.Book;
import com.example.homework05.domain.Genre;
import com.example.homework05.dto.BookDto;
import com.example.homework05.dto.BookResponseDto;
import com.example.homework05.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public void saveBook(BookDto newBook) {
        Author author = authorDao.getById(newBook.getAuthorId()).orElseThrow();
        Genre genre = genreDao.getById(newBook.getGenreId()).orElseThrow();
        Book book = new Book(null, newBook.getName(),
                author, genre);
        bookDao.insert(book);
    }

    public void updateBook(BookDto updateBook) {
        Author author =
                authorDao.getById(updateBook.getAuthorId()).orElseThrow(() -> new NotFoundException("Автор не " +
                "обнаружен!"));
        Genre genre =
                genreDao.getById(updateBook.getGenreId()).orElseThrow(() -> new NotFoundException("Жанр не " +
                "обнаружен!"));
        bookDao.getById(updateBook.getId()).orElseThrow(() -> new NotFoundException("Книга не " +
                "обнаружена!"));
        Book book = new Book(updateBook.getId(), updateBook.getName(),
                author, genre);
        bookDao.update(book);
    }

    public BookResponseDto getBookById(long id) {
        Book resultBook = bookDao.getById(id).orElseThrow(() -> new NotFoundException("Книга не " +
                "обнаружена!"));

        return new BookResponseDto(resultBook.getId(), resultBook.getName(),
                resultBook.getAuthor().getName(), resultBook.getGenre().getName());
    }

    public Long getBookCount() {
        return (long) bookDao.count();
    }

    public void deleteById(long id) {
        bookDao.deleteById(id);
    }
}
