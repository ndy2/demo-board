package com.example.demoboard.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostDto {

    @NotEmpty
    private String title;
    private String contents;
}
