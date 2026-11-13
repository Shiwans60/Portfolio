package com.Shiwans.portfolio.dao.interfaces;

import com.Shiwans.portfolio.entity.Message;

import java.util.List;

public interface MessageDAO {
    List<Message> getAllMessages();

    List<Message> findByEmail(String email);

    Message save(Message message);

    void deleteById(Integer id);
}
