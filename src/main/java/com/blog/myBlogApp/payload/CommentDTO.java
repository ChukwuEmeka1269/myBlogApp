package com.blog.myBlogApp.payload;

import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private String name;
    private String body;
    private String email;
}
