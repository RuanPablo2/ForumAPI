package com.RuanPablo2.ForumAPI.services;

import com.RuanPablo2.ForumAPI.dtos.request.UserRequestDTO;
import com.RuanPablo2.ForumAPI.dtos.response.UserResponseDTO;
import com.RuanPablo2.ForumAPI.exception.BusinessException;
import com.RuanPablo2.ForumAPI.exception.ForbiddenActionException;
import com.RuanPablo2.ForumAPI.exception.ResourceNotFoundException;
import com.RuanPablo2.ForumAPI.exception.UnauthorizedException;
import com.RuanPablo2.ForumAPI.model.User;
import com.RuanPablo2.ForumAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .filter(User::isActive)
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

    public User findById(String id){
        User result = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found", "USR-404"));
        return result;
    }

    @Transactional
    public User save(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email already registered", "USR-002");
        }

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        User user = new User(dto);

        return userRepository.save(user);
    }

    public UserResponseDTO update(String id, UserRequestDTO dto) {
        User loggedUser = getAuthenticatedUser();

        if (!loggedUser.getId().equals(id)) {
            throw new ForbiddenActionException("You can only update your own account.", "USR-403");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found", "USR-404"));

        user.updateUser(dto);
        user = userRepository.save(user);

        return new UserResponseDTO(user);
    }

    public void delete(String id) {
        User loggedUser = getAuthenticatedUser();

        if (!loggedUser.getId().equals(id)) {
            throw new ForbiddenActionException("You can only deactivate your own account.", "USR-403");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found", "USR-404"));

        user.setActive(false);
        userRepository.save(user);
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Unauthenticated user", "USR-001");
        }

        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found", "USR-404"));
    }
}