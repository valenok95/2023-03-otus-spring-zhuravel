package com.example.homework05.dao;

import com.example.homework05.domain.Author;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcTemplate jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcTemplate jdbcOperations) {
        this.jdbc = jdbcOperations;
    }

    @Override
    public int count() {
        Integer count = jdbc.getJdbcOperations().queryForObject("select count(*) from AUTHORS", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public Author insert(Author author) {
        jdbc.update("insert into AUTHORS (AUTHOR_ID,name) values (:AUTHOR_ID,:name)"
                , Map.of(
                        "AUTHOR_ID",
                        author.getId(), "name", author.getName()));
        return jdbc.queryForObject("select AUTHOR_ID, name from AUTHORS where AUTHOR_ID =:AUTHOR_ID",
                Map.of("AUTHOR_ID", author.getId()),
                new AuthorMapper());
    }

    @Override
    public Optional<Author> getById(long id) {
        return Optional.of(jdbc.queryForObject("select AUTHOR_ID, name from AUTHORS where AUTHOR_ID =:AUTHOR_ID",
                Map.of("AUTHOR_ID", id),
                new AuthorMapper()));
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select AUTHOR_ID, name from AUTHORS",
                new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from AUTHORS where AUTHOR_ID= :AUTHOR_ID", Map.of("AUTHOR_ID", id));
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Author(rs.getInt(1), rs.getString(2));
        }
    }
}
