package com.RuanPablo2.ForumAPI.controllers;

import com.RuanPablo2.ForumAPI.dtos.request.CommentRequestDTO;
import com.RuanPablo2.ForumAPI.dtos.request.PostRequestDTO;
import com.RuanPablo2.ForumAPI.dtos.response.CommentResponseDTO;
import com.RuanPablo2.ForumAPI.dtos.response.PostResponseDTO;
import com.RuanPablo2.ForumAPI.model.Comment;
import com.RuanPablo2.ForumAPI.model.Post;
import com.RuanPablo2.ForumAPI.services.CommentService;
import com.RuanPablo2.ForumAPI.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody PostRequestDTO dto) {
        PostResponseDTO post = postService.createPost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id){
        Post post = postService.findById(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostResponseDTO>> searchPosts(@RequestParam String query) {
        List<PostResponseDTO> posts = postService.searchPosts(query);
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable String id, @RequestBody PostRequestDTO dto) {
        PostResponseDTO post = postService.updatePost(id, dto);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<Page<Comment>> getComments(@PathVariable String id, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Comment> comments = commentService.getCommentsByPost(id, page, size);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponseDTO> addComment(@PathVariable String postId, @RequestBody @Valid CommentRequestDTO dto) {
        CommentResponseDTO comment = commentService.save(postId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable String postId, @PathVariable String commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.noContent().build();
    }
}