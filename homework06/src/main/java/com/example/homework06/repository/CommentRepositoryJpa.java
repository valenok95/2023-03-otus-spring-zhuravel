package com.example.homework06.repository;

import com.example.homework06.model.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    public CommentRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Comment insert(Comment comment) {
        if (comment.getId() <= 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public Comment update(Comment comment) {
        Query query = em.createQuery("update Comment c set c.text = :newText where c.id =:commentId");
        query.setParameter("commentId", comment.getId());
        query.setParameter("newText", comment.getText());
        query.executeUpdate();
        return getById(comment.getId()).get();
    }

    @Override
    public Optional<Comment> getById(long id) {
        TypedQuery<Comment> query = em.createQuery(
                "select c from Comment c where c.id = :id"
                , Comment.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Comment> getAll() {
        return em.createQuery("select c from Comment c", Comment.class).getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Comment> getAllByBookId(long id) {
        TypedQuery<Comment> query = em.createQuery(
                "select c from Comment c where c.book.id=:id"
                , Comment.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
