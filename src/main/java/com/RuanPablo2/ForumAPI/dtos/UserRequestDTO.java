package com.RuanPablo2.ForumAPI.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserRequestDTO {

    private String name;
    private String email;

    public UserRequestDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }
}