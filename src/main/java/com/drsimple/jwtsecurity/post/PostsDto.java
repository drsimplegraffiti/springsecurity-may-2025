package com.drsimple.jwtsecurity.post;

import lombok.Data;

@Data
public class PostsDto {
    private Long id;
    private String title;
    private String content;
    private String author;

}
