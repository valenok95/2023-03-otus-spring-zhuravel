package com.example.homework05.dao;

import com.example.homework05.domain.Author;
import com.example.homework05.domain.Book;
import com.example.homework05.domain.Genre;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
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
    public void insert(Book book) {
        jdbc.update("insert into BOOKS (id,name,authorID,genreID) values (:id,:name,:authorID,:genreID)"
                , Map.of(
                        "id",
                        book.getId(), "name", book.getName(), "authorID", book.getAuthor().getId(),
                        "genreID",
                        book.getGenre().getId()));
    }

    @Override
    public Book getById(long id) {
        return jdbc.queryForObject("select b.id, b.name, a.ID, a.name,g.ID, g.name from BOOKS b" +
                        "                LEFT JOIN AUTHORS a ON b.authorID = a.ID" +
                        "                LEFT JOIN GENRES g ON b.genreID = g.ID" +
                        "                where b.id =:id;",
                Map.of("id", id),
                new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select b.id, b.name, a.ID, a.name,g.ID, g.name from BOOKS b" +
                        "                LEFT JOIN AUTHORS a ON b.authorID = a.ID" +
                        "                LEFT JOIN GENRES g ON b.genreID = g.ID",
                new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from BOOKS where id= :id", Map.of("id", id));
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
