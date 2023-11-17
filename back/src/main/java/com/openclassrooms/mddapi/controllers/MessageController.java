package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.MessageDto;
import com.openclassrooms.mddapi.models.Message;
import com.openclassrooms.mddapi.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/messages")
@Log4j2
@Tag(name = "Message", description = "Message API qui permet de faire les operations sur message.")
public class MessageController {
    private final MessageService service;
    private final ModelMapper mapper;

    public MessageController(MessageService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Creation d'un commentaire
     *
     * @param messageDto Credential for creating the comment
     * @return The HTTP response
     */
    @Operation(summary = "Creation de message")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Object of MessageDto.class")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody MessageDto messageDto) {
        this.service.save(this.mapper.map(messageDto, Message.class));
        return ResponseEntity.ok().build();
    }
}
