package com.example.homework08.changelog;

import com.example.homework08.model.Author;
import com.example.homework08.model.Book;
import com.example.homework08.model.Genre;
import com.example.homework08.repository.BookRepository;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import java.util.List;

@ChangeLog
public class MongoChangelog {

    @ChangeSet(order = "002", id = "clean", author = "val")
    public void refresh(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertBooks", author = "stvort")
    public void insertBooks(BookRepository repository) {
        Genre genreRoman = new Genre("roman");
        Author authorLev = new Author("Lev Tolstoy");
        Author authorAlex = new Author("Alexandr Pushkin");

        Book book1 = new Book("654e151c3991c1466093bce3","Voyna i Mir", authorLev,
                genreRoman);
        Book book2 = new Book("Eugene Onegin", authorAlex, genreRoman);
        repository.saveAll(List.of(book1,book2));
    }
}
