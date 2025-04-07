package com.RuanPablo2.ForumAPI.controllers;

import com.RuanPablo2.ForumAPI.dtos.request.CommentRequestDTO;
import com.RuanPablo2.ForumAPI.dtos.request.PostRequestDTO;
import com.RuanPablo2.ForumAPI.dtos.response.CommentResponseDTO;
import com.RuanPablo2.ForumAPI.dtos.response.PageResponseDTO;
import com.RuanPablo2.ForumAPI.dtos.response.PageUtils;
import com.RuanPablo2.ForumAPI.dtos.response.PostResponseDTO;
import com.RuanPablo2.ForumAPI.model.Comment;
import com.RuanPablo2.ForumAPI.model.Post;
import com.RuanPablo2.ForumAPI.services.CommentService;
import com.RuanPablo2.ForumAPI.services.PostLikeService;
import com.RuanPablo2.ForumAPI.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostLikeService postLikeService;

    @GetMapping
    public PageResponseDTO<PostResponseDTO> getAllPosts(@PageableDefault(size = 3, sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostResponseDTO> page = postService.getAllPosts(pageable);
        return PageUtils.buildPageResponse(page);
    }

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
    public PageResponseDTO<PostResponseDTO> search(@RequestParam String query, @PageableDefault(size = 5, sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostResponseDTO> page = postService.searchPosts(query, pageable);
        return PageUtils.buildPageResponse(page);
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

    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> likePost(@PathVariable String postId) {
        postLikeService.likePost(postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}/like")
    public ResponseEntity<Void> unlikePost(@PathVariable String postId) {
        postLikeService.unlikePost(postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}/likes")
    public ResponseEntity<Long> countLikes(@PathVariable String postId) {
        return ResponseEntity.ok(postLikeService.countLikes(postId));
    }
}