package com.example.homework06.repository;

import com.example.homework06.model.Genre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class GenreRepositoryJdbc implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    public GenreRepositoryJdbc(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Genre> getById(long id) {
        TypedQuery<Genre> query = em.createQuery(
                "select g from Genre g where g.id = :id"
                , Genre.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
