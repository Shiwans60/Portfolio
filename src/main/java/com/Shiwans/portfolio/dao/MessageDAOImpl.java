
package com.Shiwans.portfolio.dao;

import com.Shiwans.portfolio.dao.interfaces.MessageDAO;
import com.Shiwans.portfolio.entity.Message;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageDAOImpl implements MessageDAO {

    private final EntityManager entityManager;

    public MessageDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Message> getAllMessages() {

        TypedQuery<Message> query = entityManager.createQuery(
                "SELECT m FROM Message m ORDER BY m.id DESC",
                Message.class
        );

        return query.getResultList();
    }

    @Override
    public List<Message> findByEmail(String email) {

        TypedQuery<Message> query = entityManager.createQuery(
                "SELECT m FROM Message m WHERE m.email = :email ORDER BY m.id DESC",
                Message.class
        );

        query.setParameter("email", email);

        return query.getResultList();
    }

    @Override
    public Message save(Message message) {
        return entityManager.merge(message);
    }

    @Override
    public void deleteById(Integer id) {

        Message message = entityManager.find(Message.class, id);

        if (message != null) {
            entityManager.remove(message);
        }
    }
}
