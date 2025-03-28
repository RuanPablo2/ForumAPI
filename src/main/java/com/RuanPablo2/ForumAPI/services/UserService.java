package com.RuanPablo2.ForumAPI.services;

import com.RuanPablo2.ForumAPI.dtos.UserRequestDTO;
import com.RuanPablo2.ForumAPI.dtos.response.UserResponseDTO;
import com.RuanPablo2.ForumAPI.exception.ResourceNotFoundException;
import com.RuanPablo2.ForumAPI.model.User;
import com.RuanPablo2.ForumAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserResponseDTO> findAll(){
        List<UserResponseDTO> list = userRepository.findAll().stream().map(x -> new UserResponseDTO(x)).collect(Collectors.toList());

        return list;
    }

    public User findById(String id){
        User result = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found", "USR-404"));
        return result;
    }

    public User save(UserRequestDTO dto){
        User user = new User(dto);
        return userRepository.save(user);
    }

    public UserResponseDTO update(String id, UserRequestDTO dto){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found", "USR-404"));

        user.updateUser(dto);

        user = userRepository.save(user);
        return new UserResponseDTO(user);
    }

    public void delete(String id){
        findById(id);
        userRepository.deleteById(id);
    }
}