package com.example.homework05.controller;

import com.example.homework05.domain.Author;
import com.example.homework05.domain.Book;
import com.example.homework05.domain.Genre;
import com.example.homework05.service.AuthorService;
import com.example.homework05.service.BookService;
import com.example.homework05.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @ShellMethod(value = "Create book command", key = {"cb", "createbook"})
    public String createBook(
            @ShellOption(defaultValue = "999") long id,
            @ShellOption(defaultValue = "Some book") String name,
            @ShellOption(defaultValue = "1") long authorId,
            @ShellOption(defaultValue = "1") long genreId) {
        Author author = authorService.getAuthorById(authorId);
        Genre genre = genreService.getGenreById(genreId);
        Book newBook = new Book(id, name,
                author, genre);
        bookService.saveBook(newBook);
        return String.format("Сохранена книга: %s с id %d", newBook.getName(), newBook.getId());
    }

    @ShellMethod(value = "Get book command", key = {"gb", "getbook"})
    public String getBook(
            @ShellOption(defaultValue = "1") long id) {
        Book book = bookService.getBookById(id);
        return String.format("Получена книга: %s , Автор: %s ; Жанр: %s ", book.getName(),
                book.getAuthor().getName(), book.getGenre().getName());
    }

    @ShellMethod(value = "Update book command", key = {"ub", "updatebook"})
    public String updateBook(
            @ShellOption(defaultValue = "999") long id,
            @ShellOption(defaultValue = "Some book") String name,
            @ShellOption(defaultValue = "1") long authorId,
            @ShellOption(defaultValue = "1") long genreId) {
        Author author = authorService.getAuthorById(authorId);
        Genre genre = genreService.getGenreById(genreId);
        Book newBook = new Book(id, name,
                author, genre);
        bookService.saveBook(newBook);
        return String.format("Обновлена книга: %s с id %d", newBook.getName(), newBook.getId());
    }


    @ShellMethod(value = "Get book command", key = {"db", "deletebook"})
    public String deleteBook(
            @ShellOption(defaultValue = "1") long id) {
        Book book = bookService.getBookById(id);
        bookService.deleteById(id);
        return String.format("Удалена книга: %s , Автор: %s ; Жанр: %s ", book.getName(),
                book.getAuthor().getName(), book.getGenre().getName());
    }
}
