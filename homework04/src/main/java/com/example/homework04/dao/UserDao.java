package com.example.homework04.dao;

import com.example.homework04.domain.User;

public interface UserDao {
    User getUser();

    void saveUser(User user);
}
