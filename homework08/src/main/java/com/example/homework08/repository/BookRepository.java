package com.example.homework08.repository;

import com.example.homework08.model.Book;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findAll();
}
