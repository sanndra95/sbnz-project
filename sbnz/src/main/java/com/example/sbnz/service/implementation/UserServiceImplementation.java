package com.example.sbnz.service.implementation;

import com.example.sbnz.SbnzApplication;
import com.example.sbnz.model.User;
import com.example.sbnz.repository.UserRepository;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sbnz.service.UserService;

import java.util.Collection;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public Collection<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public User findById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public void logout(String username) {
        SbnzApplication.kieSessions.remove("kieSession-"+username);
        SbnzApplication.allUsers.remove("currentUser-"+username);
    }


}
