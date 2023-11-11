package com.example.homework08.service;

import com.example.homework08.dto.BookDto;
import com.example.homework08.dto.BookResponseDto;
import com.example.homework08.mapper.BookMapper;
import com.example.homework08.model.Author;
import com.example.homework08.model.Book;
import com.example.homework08.model.Genre;
import com.example.homework08.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    @Transactional
    public BookResponseDto saveBook(BookDto newBook) {
        Author author = new Author(newBook.getAuthorName());
        Genre genre = new Genre(newBook.getGenreName());
        Book book = new Book(newBook.getName(),
                author, genre);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toResponseDto(savedBook);
    }
/*
    @Transactional
    public void updateBook(BookDto updateBook) {
        Author author =
                authorRepository.findById(updateBook.getAuthorName()).orElseThrow(() -> new NotFoundException("Автор не " +
                        "обнаружен!"));
        Genre genre =
                genreRepository.findById(updateBook.getGenreName()).orElseThrow(() -> new NotFoundException("Жанр не " +
                        "обнаружен!"));
        Book book =
                bookRepository.findById(updateBook.getId()).orElseThrow(() -> new NotFoundException(
                        "Книга не обнаружена!"));
        book.setName(updateBook.getName());
        book.setAuthor(author);
        book.setGenre(genre);
        bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public BookResponseDto getBookById(long id) {
        Book resultBook = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Книга не " +
                "обнаружена!"));
        return bookMapper.toResponseDto(resultBook);
    }

    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }*/
}
