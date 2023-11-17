package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.UserDto;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auths")
@Tag(name = "Auth", description = "Api pour s'authentifier et s'enregistrer.")
public class AuthController {
    private final UserService service;
    private final ModelMapper modelMapper;

    public AuthController(UserService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }
    @Operation(summary = "Login an user")
    @PostMapping("/login")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Api pour se connecter")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return service.createToken(loginRequest.getEmail(), loginRequest.getPassword());
    }

    /**
     * S'enregister
     *
     * @param signUpRequest les infos de l'utilisateur
     * @return Retounre une reponse avec le token
     */
    @Operation(summary = "Creation d'un nouvel user")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "S'enregister avec SignupRequest")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (service.userExistence(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email est dejà pris."));
        }
        String password = signUpRequest.getPassword();
        service.createUser(signUpRequest);
        return service.createToken(signUpRequest.getEmail(), password);
    }

    /**
     * Get a curent authenticated user
     *
     * @return The HTTP response with UserDto credential
     */
    @Operation(summary = "Retourne les infos de l'utisateur connecte")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        UserDto userDTO = modelMapper.map(service.getCurrentUser(), UserDto.class);

        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Utilisateur non trouve"));
    }
}
