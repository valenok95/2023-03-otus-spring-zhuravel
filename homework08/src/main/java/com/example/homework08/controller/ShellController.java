package com.example.homework08.controller;

import com.example.homework08.dto.BookDto;
import com.example.homework08.dto.BookResponseDto;
import com.example.homework08.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {
    private final BookService bookService;

    @ShellMethod(value = "Create book command", key = {"cb", "createbook"})
    public String createBook(
            @ShellOption(defaultValue = "Some book") String name,
            @ShellOption(defaultValue = "Pushkin") String authorId,
            @ShellOption(defaultValue = "roman") String genreId) {
        BookDto newBook = new BookDto(null, name, authorId, genreId);
        BookResponseDto responseDto = bookService.saveBook(newBook);
        return String.format("Сохранена книга: %s с id %d", responseDto.getName(), responseDto.getId());
    }
/*
    @ShellMethod(value = "Get book command", key = {"gb", "getbook"})
    public String getBook(
            @ShellOption(defaultValue = "1") long id) {
        try {
            BookResponseDto response = bookService.getBookById(id);
            return String.format("Получена книга: %s , Автор: %s ; Жанр: %s ", response.getName(),
                    response.getName(), response.getName());
        } catch (NotFoundException e) {
            return String.format("Ошибка 404: %s", e.getMessage());
        }
    }

    @ShellMethod(value = "Update book command", key = {"ub", "updatebook"})
    public String updateBook(
            @ShellOption(defaultValue = "999") long id,
            @ShellOption(defaultValue = "Some book") String name,
            @ShellOption(defaultValue = "1") long authorId,
            @ShellOption(defaultValue = "1") long genreId) {
        try {
            BookDto newBook = new BookDto(id, name, authorId, genreId);
            BookResponseDto response = bookService.saveBook(newBook);
            return String.format("Обновлена книга: %s с id %d", response.getName(), response.getId());
        } catch (NotFoundException e) {
            return String.format("Ошибка 404: %s", e.getMessage());
        }
    }


    @ShellMethod(value = "Get book command", key = {"db", "deletebook"})
    public String deleteBook(
            @ShellOption(defaultValue = "1") long id) {
        try {
            BookResponseDto deletedBook = bookService.getBookById(id);
            bookService.deleteById(id);
            return String.format("Удалена книга: %s , Автор: %s ; Жанр: %s ", deletedBook.getName(),
                    deletedBook.getName(), deletedBook.getName());
        } catch (NotFoundException e) {
            return String.format("Ошибка 404: %s", e.getMessage());
        }
    }*/
}
