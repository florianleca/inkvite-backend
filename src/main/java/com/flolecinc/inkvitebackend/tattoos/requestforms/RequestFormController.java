package com.flolecinc.inkvitebackend.tattoos.requestforms;

import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtistService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/tattoos/requests")
@AllArgsConstructor
public class RequestFormController {

    private RequestFormService requestFormService;
    private TattooArtistService tattooArtistService;

    @PostMapping("/{tattooArtistId}")
    public ResponseEntity<String> createRequest(
            @PathVariable UUID tattooArtistId,
            @RequestBody RequestFormDto requestForm) {
        var tattooArtist = tattooArtistService.retrieveTattooArtist(tattooArtistId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tattoo artist not found"));
        requestFormService.handleNewRequestForm(tattooArtist, requestForm);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tattoo project successfully created and saved");
    }

}
