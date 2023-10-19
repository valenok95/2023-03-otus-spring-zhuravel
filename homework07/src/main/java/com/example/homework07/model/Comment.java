package com.example.homework07.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "COMMENTS")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_generator")
    @SequenceGenerator(name = "comments_generator",sequenceName = "COMMENTS_SEQ",  allocationSize = 1)
    @Column(name = "COMMENT_ID")
    private long id;

    @Column(name = "TEXT")
    private String text;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID", referencedColumnName = "BOOK_ID")
    private Book book;

    public Comment(String text, Book book) {
        this.text = text;
        this.book = book;
    }
}
