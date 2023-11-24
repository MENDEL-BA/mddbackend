package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.dtos.SubjectDto;
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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/subjects")
@Log4j2
@Tag(name = "Subjects", description = " Subject API")
public class SubjectController {
    private final SubjectService service;


    public SubjectController(SubjectService service) {
        this.service = service;
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
        List<SubjectDto> subjectDtos = this.service.getAll();
        if (!subjectDtos.isEmpty()) {
            return ResponseEntity.ok().body(subjectDtos);
        }
        return ResponseEntity.noContent().build();
    }
}
