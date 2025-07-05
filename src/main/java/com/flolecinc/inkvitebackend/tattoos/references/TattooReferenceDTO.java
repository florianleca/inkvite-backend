package com.flolecinc.inkvitebackend.tattoos.references;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TattooReferenceDTO {

    @NotBlank
    private String imagePath;

    private String comment;

}
