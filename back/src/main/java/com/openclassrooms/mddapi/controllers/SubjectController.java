package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.services.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/subjects")
@Log4j2
@Tag(name = "Subjects", description = " Subject API")
public class SubjectController {
    private final SubjectService service;
    private final ModelMapper mapper;

    public SubjectController(SubjectService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Get all subjects
     *
     * @return The HTTP response with a List of Subjects
     */
    @Operation(summary = "Retourne tous les sujets")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Subject> subjects = this.service.getAll();
        if (!subjects.isEmpty()) {
            return ResponseEntity.ok().body(this.mapper.map(subjects, Subject.class));
        }
        return ResponseEntity.notFound().build();
    }
}
