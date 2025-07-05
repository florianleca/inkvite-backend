package com.flolecinc.inkvitebackend.tattoos.references.images;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/references-images")
@AllArgsConstructor
public class ReferenceImageController {

    private final ReferenceImageService referenceImageService;

    @PostMapping(value = "/upload-from-device", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TempImageDTO> uploadImageFromDevice(@RequestParam("file") MultipartFile file) {
        TempImageDTO tempImage = referenceImageService.uploadImageFromDevice(file);
        return ResponseEntity.ok(tempImage);
    }
    
}
