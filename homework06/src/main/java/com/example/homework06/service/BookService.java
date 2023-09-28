package com.example.homework06.service;

import com.example.homework06.dto.BookDto;
import com.example.homework06.dto.BookResponseDto;
import com.example.homework06.exceptions.NotFoundException;
import com.example.homework06.model.Author;
import com.example.homework06.model.Book;
import com.example.homework06.model.Genre;
import com.example.homework06.repository.AuthorRepository;
import com.example.homework06.repository.BookRepository;
import com.example.homework06.repository.GenreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Transactional
    public BookResponseDto saveBook(BookDto newBook) {
        Author author = authorRepository.getById(newBook.getAuthorId()).orElseThrow();
        Genre genre = genreRepository.getById(newBook.getGenreId()).orElseThrow();
        Book book = new Book(newBook.getName(),
                author, genre);
        Book savedBook = bookRepository.insert(book);
        return new BookResponseDto(savedBook.getId(), savedBook.getName(),
                savedBook.getAuthor().getName(), savedBook.getGenre().getName());
    }

    @Transactional
    public void updateBook(BookDto updateBook) {
        Author author =
                authorRepository.getById(updateBook.getAuthorId()).orElseThrow(() -> new NotFoundException("Автор не " +
                        "обнаружен!"));
        Genre genre =
                genreRepository.getById(updateBook.getGenreId()).orElseThrow(() -> new NotFoundException("Жанр не " +
                        "обнаружен!"));
        bookRepository.getById(updateBook.getId()).orElseThrow(() -> new NotFoundException("Книга не " +
                "обнаружена!"));
        Book book = new Book(updateBook.getId(), updateBook.getName(),
                author, genre);
        bookRepository.update(book);
    }

    @Transactional
    public BookResponseDto getBookById(long id) {
        Book resultBook = bookRepository.getById(id).orElseThrow(() -> new NotFoundException("Книга не " +
                "обнаружена!"));

        return new BookResponseDto(resultBook.getId(), resultBook.getName(),
                resultBook.getAuthor().getName(), resultBook.getGenre().getName());
    }

    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}
