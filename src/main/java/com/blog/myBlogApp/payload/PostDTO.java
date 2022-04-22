package com.blog.myBlogApp.payload;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long id;
    @NotEmpty
    @Size(min = 5, message = "Post title must contain minimum of 5 characters")
    private String title;

    @Size(min =  10, message = "Post description should have a minimum of 10 characters")
    private String description;

    @NotEmpty
    private String content;

    private Set<CommentDTO> comments;
}
