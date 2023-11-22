package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.UserUpdateDto;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
@Log4j2
@Tag(name = "User", description = " User API.")
public class UserController {
    private final UserService service;
    private final ModelMapper mapper;

    public UserController(UserService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Get a user by id
     *
     * @param id id of a User
     * @return le HTTP response with User
     */
    @Operation(summary = "Get an user by id")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@Parameter(description = "id du user") @PathVariable("id") String id) {
        try {
            User user = this.service.getById(Long.valueOf(id));
            if (user == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(this.mapper.map(user, User.class));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Delete a user by id
     *
     * @param id id of a User
     * @return The HTTP response
     */
    @Operation(summary = "Delete an user by id")
    @SecurityRequirement(name = "Bearer Authentication")
    @Parameter(description = "id of User to be deleted")
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        try {
            User user = this.service.getById(Long.valueOf(id));

            if (user == null) {
                return ResponseEntity.noContent().build();
            }

            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (!Objects.equals(userDetails.getUsername(), user.getEmail())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            this.service.delete(user);
            return ResponseEntity.ok().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update user email, name and password
     *
     * @param userUpdateDto The new user data
     * @return The HTTP response with User
     */
    @Operation(summary = "Update an user")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Object of UserUpdateDto.class")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody UserUpdateDto userUpdateDto) {
        User userOld = this.service.getById(userUpdateDto.getId());
        if (userOld == null) {
            return ResponseEntity.notFound().build();
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!Objects.equals(userDetails.getUsername(), userOld.getEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return this.service.update(this.mapper.map(userUpdateDto, User.class));
    }
}
