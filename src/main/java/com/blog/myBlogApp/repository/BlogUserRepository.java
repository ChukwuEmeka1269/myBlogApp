package com.blog.myBlogApp.repository;

import com.blog.myBlogApp.model.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogUserRepository extends JpaRepository<BlogUser, Long> {
    Optional<BlogUser> findByEmail(String email);
    Optional<BlogUser> findByUsernameOrEmail(String username, String email);
    Optional<BlogUser> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
