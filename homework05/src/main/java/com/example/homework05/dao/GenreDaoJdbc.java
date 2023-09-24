package com.example.homework05.dao;

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
    public Genre insert(Genre genre) {
        jdbc.update("insert into GENRES (GENRE_ID,name) values (:GENRE_ID,:name)"
                , Map.of(
                        "GENRE_ID",
                        genre.getId(), "name", genre.getName()));
        return jdbc.queryForObject("select GENRE_ID, name from GENRES where GENRE_ID =:GENRE_ID",
                Map.of("GENRE_ID", genre.getId()),
                new GenreDaoJdbc.GenreMapper());
    }

    @Override
    public Optional<Genre> getById(long id) {
        return Optional.of(jdbc.queryForObject("select GENRE_ID, name from GENRES where GENRE_ID =:GENRE_ID",
                Map.of("GENRE_ID", id),
                new GenreDaoJdbc.GenreMapper()));
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select GENRE_ID, name from GENRES",
                new GenreDaoJdbc.GenreMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from GENRES where GENRE_ID= :GENRE_ID", Map.of("GENRE_ID", id));
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Genre(rs.getInt(1), rs.getString(2));
        }
    }
}
