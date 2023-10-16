package com.example.homework04.dao;

import com.example.homework04.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class ConsoleUserDao implements UserDao {
    private User user = null;

    @Override
    public User getUser() {
        return user;
    }
    
    @Override
    public void saveUser(User user) {
        this.user = user;
    }
}
