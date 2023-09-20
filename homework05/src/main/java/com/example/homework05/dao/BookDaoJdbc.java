package com.example.homework05.dao;

import com.example.homework05.domain.Author;
import com.example.homework05.domain.Book;
import com.example.homework05.domain.Genre;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcTemplate jdbc;

    public BookDaoJdbc(NamedParameterJdbcTemplate jdbcOperations) {
        this.jdbc = jdbcOperations;
    }

    @Override
    public int count() {
        Integer count = jdbc.getJdbcOperations().queryForObject("select count(*) from BOOKS", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public Book insert(Book book) {
        jdbc.update("insert into BOOKS (BOOK_ID,name,AUTHOR_ID,GENRE_ID) values (:BOOK_ID,:name," +
                        ":AUTHOR_ID,:GENRE_ID)"
                , Map.of(
                        "BOOK_ID",
                        book.getId(), "name", book.getName(), "AUTHOR_ID", book.getAuthor().getId(),
                        "GENRE_ID",
                        book.getGenre().getId()));
        return jdbc.queryForObject("select b.BOOK_ID, b.name, a.AUTHOR_ID, a.name,g.GENRE_ID, g.name from BOOKS b" +
                        "                LEFT JOIN AUTHORS a ON b.AUTHOR_ID = a.AUTHOR_ID" +
                        "                LEFT JOIN GENRES g ON b.GENRE_ID = g.GENRE_ID" +
                        "                where b.BOOK_ID =:BOOK_ID;",
                Map.of("BOOK_ID", book.getId()),
                new BookMapper());
    }

    @Override
    public Book update(Book book) {
        jdbc.update("UPDATE BOOKS SET name =:name, AUTHOR_ID=:AUTHOR_ID," +
                        "GENRE_ID=:GENRE_ID WHERE BOOK_ID=:BOOK_ID"
                , Map.of(
                        "BOOK_ID",
                        book.getId(), "name", book.getName(), "AUTHOR_ID", book.getAuthor().getId(),
                        "GENRE_ID",
                        book.getGenre().getId()));
        return jdbc.queryForObject("select b.BOOK_ID, b.name, a.AUTHOR_ID, a.name,g.GENRE_ID, g.name from BOOKS b" +
                        "                LEFT JOIN AUTHORS a ON b.AUTHOR_ID = a.AUTHOR_ID" +
                        "                LEFT JOIN GENRES g ON b.GENRE_ID = g.GENRE_ID" +
                        "                where b.BOOK_ID =:BOOK_ID;",
                Map.of("BOOK_ID", book.getId()),
                new BookMapper());

    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.of(jdbc.queryForObject("select b.BOOK_ID, b.name, a.AUTHOR_ID, a.name,g.GENRE_ID, g.name from BOOKS b" +
                        "                LEFT JOIN AUTHORS a ON b.AUTHOR_ID = a.AUTHOR_ID" +
                        "                LEFT JOIN GENRES g ON b.GENRE_ID = g.GENRE_ID" +
                        "                where b.BOOK_ID =:BOOK_ID;",
                Map.of("BOOK_ID", id),
                new BookMapper()));
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select b.BOOK_ID, b.name, a.AUTHOR_ID, a.name,g.GENRE_ID, g.name from BOOKS b" +
                        "                LEFT JOIN AUTHORS a ON b.AUTHOR_ID = a.AUTHOR_ID" +
                        "                LEFT JOIN GENRES g ON b.GENRE_ID = g.GENRE_ID",
                new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from BOOKS where BOOK_ID= :BOOK_ID", Map.of("BOOK_ID", id));
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(rs.getInt(1), rs.getString(2), new Author(rs.getInt(3),
                    rs.getString(4)),
                    new Genre(rs.getInt(5), rs.getString(6)));
        }
    }
}
