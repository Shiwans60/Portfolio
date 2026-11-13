package com.Shiwans.portfolio.service;

import com.Shiwans.portfolio.dao.interfaces.MessageDAO;
import com.Shiwans.portfolio.entity.Message;
import com.Shiwans.portfolio.service.interfaces.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageDAO messageDAO;

    public MessageServiceImpl(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    @Override
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    @Override
    public List<Message> findByEmail(String email) {
        return messageDAO.findByEmail(email);
    }

    @Transactional
    @Override
    public Message save(Message message) {
        return messageDAO.save(message);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        messageDAO.deleteById(id);
    }
}