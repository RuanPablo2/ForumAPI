package com.RuanPablo2.ForumAPI.config;

import com.RuanPablo2.ForumAPI.dtos.response.AuthorResponseDTO;
import com.RuanPablo2.ForumAPI.model.Post;
import com.RuanPablo2.ForumAPI.model.User;
import com.RuanPablo2.ForumAPI.repositories.PostRepository;
import com.RuanPablo2.ForumAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        userRepository.deleteAll();
        postRepository.deleteAll();

        User maria = new User(null, "Maria Silva", "maria@email.com", "123", null);
        User joao = new User(null, "Joao Santos", "joao@email.com", "123", null);
        User jose = new User(null, "Jose Oliveira", "jose@email.com", "123", null);

        userRepository.saveAll(Arrays.asList(maria, joao, jose));

        Post post1 = new Post(null, sdf.parse("21/03/2025"), "Partiu viagem", "Vou viajar para São Paulo. Abraços!", new AuthorResponseDTO(maria), 0);
        Post post2 = new Post(null, sdf.parse("23/03/2025"), "Bom dia", "Acordei feliz hoje!", new AuthorResponseDTO(maria), 0);


        postRepository.saveAll(Arrays.asList(post1, post2));

        maria.getPosts().addAll(Arrays.asList(post1, post2));

        userRepository.save(maria);
    }
}