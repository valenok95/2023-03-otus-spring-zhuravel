package com.example.homework05.dao;

import com.example.homework05.domain.Genre;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcTemplate jdbc;

    public GenreDaoJdbc(NamedParameterJdbcTemplate jdbcOperations) {
        this.jdbc = jdbcOperations;
    }

    @Override
    public int count() {
        Integer count = jdbc.getJdbcOperations().queryForObject("select count(*) from GENRES", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public void insert(Genre author) {
        jdbc.update("insert into GENRES (id,name) values (:id,:name)"
                , Map.of(
                        "id",
                        author.getId(), "name", author.getName()));
    }

    @Override
    public Genre getById(long id) {
        return jdbc.queryForObject("select id, name from GENRES where id =:id",
                Map.of("id", id),
                new GenreDaoJdbc.GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select id, name from GENRES",
                new GenreDaoJdbc.GenreMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from GENRES where id= :id", Map.of("id", id));
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Genre(rs.getInt(1), rs.getString(2));
        }
    }
}
