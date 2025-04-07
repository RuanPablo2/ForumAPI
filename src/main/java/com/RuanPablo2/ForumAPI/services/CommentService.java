package com.RuanPablo2.ForumAPI.services;

import com.RuanPablo2.ForumAPI.dtos.request.CommentRequestDTO;
import com.RuanPablo2.ForumAPI.dtos.response.AuthorResponseDTO;
import com.RuanPablo2.ForumAPI.dtos.response.CommentResponseDTO;
import com.RuanPablo2.ForumAPI.exception.ForbiddenActionException;
import com.RuanPablo2.ForumAPI.exception.ResourceNotFoundException;
import com.RuanPablo2.ForumAPI.model.Comment;
import com.RuanPablo2.ForumAPI.model.Post;
import com.RuanPablo2.ForumAPI.model.User;
import com.RuanPablo2.ForumAPI.model.enums.Role;
import com.RuanPablo2.ForumAPI.repositories.CommentRepository;
import com.RuanPablo2.ForumAPI.repositories.PostRepository;
import com.RuanPablo2.ForumAPI.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    public CommentResponseDTO save(String postId, CommentRequestDTO dto) {
        User loggedUser = userService.getAuthenticatedUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found", "PST-404"));

        Comment comment = new Comment(null, dto.getText(), Instant.now(), new AuthorResponseDTO(loggedUser), postId);
        comment = commentRepository.save(comment);

        post.setTotalComments(post.getTotalComments() + 1);
        postRepository.save(post);

        return new CommentResponseDTO(comment);
    }

    public Page<Comment> getCommentsByPost(String postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        return commentRepository.findByPostId(postId, pageable);
    }

    public void deleteComment(String postId, String commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String loggedUserId = userDetails.getId();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found", "CMT-404"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found", "PST-404"));

        boolean isCommentOwner = comment.getAuthor().getId().equals(loggedUserId);
        boolean isPostOwner = post.getAuthor().getId().equals(loggedUserId);
        boolean isAdmin = userDetails.getRole() == Role.ROLE_ADMIN;

        boolean hasPermission = isCommentOwner || isPostOwner || isAdmin;
        if (!hasPermission) {
            throw new ForbiddenActionException("You do not have permission to delete this comment.", "CMT-403");
        }

        commentRepository.delete(comment);
        post.setTotalComments(Math.max(0, post.getTotalComments() - 1));
        postRepository.save(post);
    }

    public Page<CommentResponseDTO> getCommentsByUser(String userId, Pageable pageable) {
        userService.findById(userId);

        Page<Comment> page = commentRepository.findByAuthorId(userId, pageable);
        return page.map(CommentResponseDTO::new);
    }
}