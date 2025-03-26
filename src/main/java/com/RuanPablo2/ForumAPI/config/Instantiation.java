package com.RuanPablo2.ForumAPI.config;

import com.RuanPablo2.ForumAPI.model.User;
import com.RuanPablo2.ForumAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        userRepository.deleteAll();

        User maria = new User(null, "Maria Silva", "maria@email.com");
        User joao = new User(null, "Joao Santos", "joao@email.com");
        User jose = new User(null, "Jose Oliveira", "jose@email.com");

        userRepository.saveAll(Arrays.asList(maria, joao, jose));
    }
}