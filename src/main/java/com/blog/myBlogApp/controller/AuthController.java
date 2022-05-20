package com.blog.myBlogApp.controller;

import com.blog.myBlogApp.model.BlogUser;
import com.blog.myBlogApp.model.Role;
import com.blog.myBlogApp.payload.ApiOpResponse;
import com.blog.myBlogApp.payload.JwtAuthResponse;
import com.blog.myBlogApp.payload.LoginDTO;
import com.blog.myBlogApp.payload.SignupDTO;
import com.blog.myBlogApp.repository.BlogUserRepository;
import com.blog.myBlogApp.repository.RoleRepository;
import com.blog.myBlogApp.security.JwtTokenProvider;
import com.blog.myBlogApp.utils.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private BlogUserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDTO loginDTO){
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsernameOrEmail(),
                        loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String token = tokenProvider.generateToken(authenticate);

        return ResponseEntity.ok(new JwtAuthResponse(token));
    }


    @PostMapping("/signup")
    public ResponseEntity<ApiOpResponse> signup(@RequestBody SignupDTO signupDTO){
        ApiOpResponse message = new ApiOpResponse();

        if(Boolean.TRUE.equals(userRepository.existsByUsername(signupDTO.getUsername()))){
            message.setMessage(AppConstants.USER_ALREADY_EXIST_MESSAGE);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        if(Boolean.TRUE.equals(userRepository.existsByEmail(signupDTO.getEmail()))){
            message.setMessage(AppConstants.EMAIL_ALREADY_EXIST_MESSAGE);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        BlogUser blogUser = new BlogUser();
        mapper.map(signupDTO, blogUser);
        blogUser.setPassword(encoder.encode(signupDTO.getPassword()));
        blogUser.setRoles(Collections.singleton(roleRepository.findByName("ROLE_ADMIN").orElseThrow()));
        userRepository.save(blogUser);
        message.setMessage(AppConstants.SUCCESSFUL_SIGNUP_MESSAGE);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }


}
