package com.RuanPablo2.ForumAPI.controllers;

import com.RuanPablo2.ForumAPI.dtos.request.UserRequestDTO;
import com.RuanPablo2.ForumAPI.dtos.response.UserResponseDTO;
import com.RuanPablo2.ForumAPI.model.Post;
import com.RuanPablo2.ForumAPI.model.User;
import com.RuanPablo2.ForumAPI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(){
        List<UserResponseDTO> list = userService.findAll();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable String id){
        User user = userService.findById(id);
        return ResponseEntity.ok(new UserResponseDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody UserRequestDTO dto){
        userService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<Post>> findPostsByUser(@PathVariable String id){
        User user = userService.findById(id);
        return ResponseEntity.ok(user.getPosts());
    }
}