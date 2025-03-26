package com.RuanPablo2.ForumAPI.services;

import com.RuanPablo2.ForumAPI.model.User;
import com.RuanPablo2.ForumAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }
}