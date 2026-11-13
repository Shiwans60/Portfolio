package com.Shiwans.portfolio.service.interfaces;

import com.Shiwans.portfolio.entity.Message;

import java.util.List;

public interface MessageService {
    List<Message> getAllMessages();

    List<Message> findByEmail(String email);

    Message save(Message message);

    void deleteById(Integer id);
}