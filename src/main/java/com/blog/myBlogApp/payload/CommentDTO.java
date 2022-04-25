package com.blog.myBlogApp.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDTO {
    private Long id;

    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @NotEmpty
    @Size(min = 10, message = "body should have a minimum of 5 characters to describe your thoughts.")
    private String body;

    @NotEmpty(message = "Email should not be null or empty")
    @Email(regexp = ".+@.+\\..+")
    private String email;
}
