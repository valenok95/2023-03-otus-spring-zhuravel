package com.example.homework03.dao;

import com.example.homework03.domain.User;
import java.util.List;

public interface UserDao {
    User getUser();

    void saveUser(User user);
}
