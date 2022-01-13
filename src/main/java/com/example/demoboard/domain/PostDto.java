package com.example.demoboard.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostDto {

    @NotEmpty
    private String title;
    private String contents;
}
