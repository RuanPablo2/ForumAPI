package com.RuanPablo2.ForumAPI.controllers;

import com.RuanPablo2.ForumAPI.dtos.request.UserRequestDTO;
import com.RuanPablo2.ForumAPI.dtos.response.*;
import com.RuanPablo2.ForumAPI.model.User;
import com.RuanPablo2.ForumAPI.services.CommentService;
import com.RuanPablo2.ForumAPI.services.PostService;
import com.RuanPablo2.ForumAPI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

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

    @GetMapping("/{userId}/posts")
    public PageResponseDTO<PostResponseDTO> getPostsByUser(@PathVariable String userId, @PageableDefault(size = 5, sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostResponseDTO> page = postService.getPostsByUser(userId, pageable);
        return PageUtils.buildPageResponse(page);
    }

    @GetMapping("/{userId}/comments")
    public PageResponseDTO<CommentResponseDTO> getCommentsByUser(@PathVariable String userId, @PageableDefault(size = 5, sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CommentResponseDTO> page = commentService.getCommentsByUser(userId, pageable);
        return PageUtils.buildPageResponse(page);
    }
}