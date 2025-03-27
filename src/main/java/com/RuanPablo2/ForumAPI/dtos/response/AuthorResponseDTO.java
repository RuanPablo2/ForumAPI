package com.RuanPablo2.ForumAPI.dtos.response;

import com.RuanPablo2.ForumAPI.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class AuthorResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;

    public AuthorResponseDTO(User user){
        id = user.getId();
        name = user.getName();
    }
}