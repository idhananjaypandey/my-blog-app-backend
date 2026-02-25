package com.dhananjay.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String username;
    private String email;
    private LocalDateTime createdAt;
}