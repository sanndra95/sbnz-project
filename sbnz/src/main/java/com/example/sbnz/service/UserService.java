package com.example.sbnz.service;

import com.example.sbnz.model.User;

import java.util.Collection;

public interface UserService {

    User create(User user);

    Collection<User> getAll();

    User findByUsername(String username);

    User findById(Long id);
}
