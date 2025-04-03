package com.RuanPablo2.ForumAPI.config;

import com.RuanPablo2.ForumAPI.dtos.response.AuthorResponseDTO;
import com.RuanPablo2.ForumAPI.model.Comment;
import com.RuanPablo2.ForumAPI.model.Post;
import com.RuanPablo2.ForumAPI.model.User;
import com.RuanPablo2.ForumAPI.model.enums.Role;
import com.RuanPablo2.ForumAPI.repositories.CommentRepository;
import com.RuanPablo2.ForumAPI.repositories.PostRepository;
import com.RuanPablo2.ForumAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        userRepository.deleteAll();
        postRepository.deleteAll();
        commentRepository.deleteAll();

        User maria = new User(null, "Maria Silva", "maria@email.com", passwordEncoder.encode("123"), Role.ROLE_USER);
        User joao = new User(null, "Joao Santos", "joao@email.com", passwordEncoder.encode("123"), Role.ROLE_USER);
        User jose = new User(null, "Jose Oliveira", "jose@email.com", passwordEncoder.encode("123"), Role.ROLE_USER);

        userRepository.saveAll(Arrays.asList(maria, joao, jose));

        Post post1 = new Post(null, LocalDate.parse("21/03/2025", dtf).atStartOfDay(ZoneOffset.UTC).toInstant(), "Vou viajar para São Paulo. Abraços!", new AuthorResponseDTO(maria), 0);
        Post post2 = new Post(null, LocalDate.parse("23/03/2025", dtf).atStartOfDay(ZoneOffset.UTC).toInstant(), "Acordei feliz hoje!", new AuthorResponseDTO(maria), 0);

        postRepository.saveAll(Arrays.asList(post1, post2));

        Comment c1 = new Comment(null, "Boa viagem mano!", LocalDate.parse("21/03/2025", dtf).atStartOfDay(ZoneOffset.UTC).toInstant(), new AuthorResponseDTO(joao), post1.getId());
        Comment c2 = new Comment(null, "Aproveite", LocalDate.parse("22/03/2025", dtf).atStartOfDay(ZoneOffset.UTC).toInstant(), new AuthorResponseDTO(jose), post1.getId());
        Comment c3 = new Comment(null, "Tenha um ótimo dia!", LocalDate.parse("23/03/2025", dtf).atStartOfDay(ZoneOffset.UTC).toInstant(), new AuthorResponseDTO(joao), post2.getId());

        commentRepository.saveAll(Arrays.asList(c1, c2, c3));

        post1.setTotalComments(2);
        post2.setTotalComments(1);

        postRepository.saveAll(Arrays.asList(post1, post2));

        maria.getPosts().addAll(Arrays.asList(post1, post2));
        userRepository.save(maria);
    }
}