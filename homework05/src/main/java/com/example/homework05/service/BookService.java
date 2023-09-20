package com.example.homework05.service;

import com.example.homework05.dao.BookDao;
import com.example.homework05.domain.Author;
import com.example.homework05.domain.Book;
import com.example.homework05.domain.Genre;
import com.example.homework05.dto.BookDto;
import com.example.homework05.dto.BookResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    public void saveBook(BookDto newBook) {
        Author author = authorService.getAuthorById(newBook.getAuthorId());
        Genre genre = genreService.getGenreById(newBook.getGenreId());
        Book book = new Book(newBook.getId(), newBook.getName(),
                author, genre);
        bookDao.insert(book);
    }

    public void updateBook(BookDto updateBook) {
        Author author = authorService.getAuthorById(updateBook.getAuthorId());
        Genre genre = genreService.getGenreById(updateBook.getGenreId());
        Book book = new Book(updateBook.getId(), updateBook.getName(),
                author, genre);
        bookDao.update(book);
    }

    public BookResponseDto getBookById(long id) {
        Book resultBook = bookDao.getById(id).orElseThrow();

        return new BookResponseDto(resultBook.getId(), resultBook.getName(),
                resultBook.getAuthor().getName(), resultBook.getGenre().getName());
    }

    public void deleteById(long id) {
        bookDao.deleteById(id);
    }
}
