package com.example.homework07.service;

import com.example.homework07.dto.BookDto;
import com.example.homework07.dto.BookResponseDto;
import com.example.homework07.exceptions.NotFoundException;
import com.example.homework07.model.Author;
import com.example.homework07.model.Book;
import com.example.homework07.model.Genre;
import com.example.homework07.repository.AuthorRepository;
import com.example.homework07.repository.BookRepository;
import com.example.homework07.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Transactional
    public BookResponseDto saveBook(BookDto newBook) {
        Author author = authorRepository.findById(newBook.getAuthorId()).orElseThrow();
        Genre genre = genreRepository.findById(newBook.getGenreId()).orElseThrow();
        Book book = new Book(newBook.getName(),
                author, genre);
        Book savedBook = bookRepository.save(book);
        return new BookResponseDto(savedBook.getId(), savedBook.getName(),
                savedBook.getAuthor().getName(), savedBook.getGenre().getName());
    }

    @Transactional
    public void updateBook(BookDto updateBook) {
        Author author =
                authorRepository.findById(updateBook.getAuthorId()).orElseThrow(() -> new NotFoundException("Автор не " +
                        "обнаружен!"));
        Genre genre =
                genreRepository.findById(updateBook.getGenreId()).orElseThrow(() -> new NotFoundException("Жанр не " +
                        "обнаружен!"));
        Book book =
                bookRepository.findById(updateBook.getId()).orElseThrow(() -> new NotFoundException(
                        "Книга не " +
                        "обнаружена!"));
        book.setName(updateBook.getName());
        book.setAuthor(author);
        book.setGenre(genre);
        bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public BookResponseDto getBookById(long id) {
        Book resultBook = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Книга не " +
                "обнаружена!"));

        return new BookResponseDto(resultBook.getId(), resultBook.getName(),
                resultBook.getAuthor().getName(), resultBook.getGenre().getName());
    }

    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}
