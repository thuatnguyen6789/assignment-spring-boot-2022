package com.example.springbootthuatdev.entity.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LoginDto {
    private String username;
    private String passwordHash;
}
