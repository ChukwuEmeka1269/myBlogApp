package com.blog.myBlogApp.payload;

import lombok.*;

import java.util.List;

@Getter
@Setter@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private List<PostDTO> contents;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
