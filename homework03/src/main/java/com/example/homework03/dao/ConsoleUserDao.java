package com.example.homework03.dao;

import com.example.homework03.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class ConsoleUserDao implements UserDao {
    private User user = null;

    @Override
    public User getUser() {
        return user;
    }
    
    @Override
    public String getFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }

    @Override
    public void saveUser(User user) {
        this.user = user;
    }
}
