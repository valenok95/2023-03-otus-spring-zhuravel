package com.example.homework06.repository;

import com.example.homework06.model.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

@PersistenceContext
private final EntityManager em;

    public AuthorRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Author> getById(long id) {
        TypedQuery<Author> query = em.createQuery(
                "select a from Author a where a.id = :id"
                , Author.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
