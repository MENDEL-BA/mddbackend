package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.SubscriptionDto;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.services.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/subscriptions")
@Log4j2
@Tag(name = "Subscription", description = " Subscription API")
public class SubscriptionController {
    private final SubscriptionService service;
    private final ModelMapper mapper;

    public SubscriptionController(SubscriptionService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Souscription a un sujet
     *
     * @param subscriptionDto le corps
     * @return le HTTP response
     */
    @Operation(summary = "Creation Souscription a un sujet")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Object of SubscriptionDto.class")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/sub")
    public ResponseEntity<?> subscription(@Valid @RequestBody SubscriptionDto subscriptionDto) {
        this.service.subscribe(this.mapper.map(subscriptionDto, Subscription.class));
        return ResponseEntity.ok().build();
    }

    /**
     * Suppression d'une souscription
     *
     * @param subscriptionDto
     * @return le HTTP response
     */
    @Operation(summary = "Suppression d'une souscription")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Object of SubscriptionDto.class")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/unsubscription")
    public ResponseEntity<?> unsubscription(@Valid @RequestBody SubscriptionDto subscriptionDto) {
        this.service.unsubscribe(this.mapper.map(subscriptionDto, Subscription.class));
        return ResponseEntity.ok().build();
    }
}
