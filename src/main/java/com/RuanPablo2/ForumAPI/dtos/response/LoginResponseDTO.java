package com.RuanPablo2.ForumAPI.dtos.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String token;
    private String userId;
    private String name;
}