package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

    User getById(Long userId);

    User createUser(SignupRequest signupRequest);

    void delete(User user);

    ResponseEntity<?> update(User user);

    boolean userExistence(String email);

    User getCurrentUser();

    ResponseEntity<?> createToken(String mail, String password);
}
