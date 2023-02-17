package com.distribuida.servicios;

import com.distribuida.db.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;



import java.util.List;


@ApplicationScoped
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext(unitName = "books-JPA")
    private EntityManager entityManager;
    @Override
    public List<Book> findAll() {
        try {
            TypedQuery<Book> query = entityManager.createQuery("SELECT b FROM Book b ORDER BY b.id ASC", Book.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public Book findById(Integer id) {
        return entityManager.find(Book.class, Integer.valueOf(id));
    }

    @Override
    public void insert(Book book) {
        entityManager.persist(book);
    }


    @Override
    public void update(Book book) {
        Book updatedBook = entityManager.find(Book.class, book.getId());
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setIsbn(book.getIsbn());
        updatedBook.setTitle(book.getTitle());
        updatedBook.setPrice(book.getPrice());
        entityManager.merge(updatedBook);
    }


    @Override
    public void delete(Integer id) {

        entityManager.remove(findById(id));
        }


    }



