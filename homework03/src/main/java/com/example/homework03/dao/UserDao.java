package com.example.homework03.dao;

import com.example.homework03.domain.User;
import java.util.List;

public interface UserDao {
    User getUser();

    String getFullName();

    void saveUser(User user);
}
