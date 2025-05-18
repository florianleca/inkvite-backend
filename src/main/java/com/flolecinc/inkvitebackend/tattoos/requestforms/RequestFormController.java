package com.flolecinc.inkvitebackend.tattoos.requestforms;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tattoos/requests")
@AllArgsConstructor
public class RequestFormController {

    private RequestFormService requestFormService;

    @PostMapping("/{tattooArtistId}")
    public ResponseEntity<String> createRequest(
            @PathVariable UUID tattooArtistId,
            @RequestBody @Valid RequestFormDto requestForm) {
        requestFormService.handleNewRequestForm(tattooArtistId, requestForm);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tattoo project successfully created and saved");
    }

}
