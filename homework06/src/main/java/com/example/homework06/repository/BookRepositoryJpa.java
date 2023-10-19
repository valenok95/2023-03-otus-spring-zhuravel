package com.example.homework06.repository;

import com.example.homework06.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private final EntityManager em;

    public BookRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Book insert(Book book) {
        if (Objects.nonNull(book.getId())) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Book update(Book book) {
        Query query = em.createQuery("update Book b set b.name = :newName, b.author = :newAuthor," +
                " b.genre = :newGenre where b.id = :bookId");
        query.setParameter("bookId", book.getId());
        query.setParameter("newName", book.getName());
        query.setParameter("newAuthor", book.getAuthor());
        query.setParameter("newGenre", book.getGenre());
        query.executeUpdate();
        return getById(book.getId()).get();
    }

    @Override
    public Optional<Book> getById(long id) {
        TypedQuery<Book> query = em.createQuery(
                "select b from Book b where b.id = :id"
                , Book.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAll() {
        return em.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
