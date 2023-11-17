package com.openclassrooms.mddapi.services.impl;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public User getById(Long userId) {
        return this.userRepository.findById(userId).orElse(null);
    }

    @Override
    @Transactional
    public User createUser(SignupRequest signupRequest) {
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User already exists: " + signupRequest.getEmail());
        }
        String password = encoder.encode(signupRequest.getPassword());
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setFirstName(signupRequest.getFirstName());
        user.setPassword(password);
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        if (userExistence(user.getEmail())) {
            this.userRepository.delete(user);
        } else throw new RestClientException("User isn't found");
    }

    @Override
    public ResponseEntity<?> update(User user) {
        User oldUser = this.userRepository.findById(user.getId()).orElse(null);
        if (oldUser != null && userExistence(oldUser.getEmail())) {
            User newUser = new User();
            newUser.setId(user.getId());
            newUser.setEmail(user.getEmail());
            newUser.setFirstName(user.getFirstName());
            String passWord = encoder.encode(user.getPassword());
            newUser.setPassword(passWord);
            newUser.setSubjects(user.getSubjects());
            newUser.setCreatedAt(user.getCreatedAt());
            newUser.setUpdatedAt(LocalDateTime.now());
            this.userRepository.save(newUser);
            return createToken(user.getEmail(), user.getPassword());
        } else throw new RestClientException("User isn't found");
    }


    public boolean userExistence(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return userRepository.findByEmail(currentUserName).orElseThrow(() -> new RestClientException("User with this Mail not found"));
        }
        return null;
    }

    @Override
    public ResponseEntity<?> createToken(String mail, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(mail, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getUsername()));
    }
}
