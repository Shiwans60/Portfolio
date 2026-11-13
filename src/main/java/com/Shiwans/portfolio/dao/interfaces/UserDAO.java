package com.Shiwans.portfolio.dao.interfaces;

import com.Shiwans.portfolio.entity.User;

public interface UserDAO {
    User findByUserName(String userName);
}
