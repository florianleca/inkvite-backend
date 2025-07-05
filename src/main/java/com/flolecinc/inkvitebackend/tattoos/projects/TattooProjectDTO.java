package com.flolecinc.inkvitebackend.tattoos.projects;

import com.flolecinc.inkvitebackend.tattoos.references.TattooReferenceDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TattooProjectDTO {

    @NotNull
    private LocalDate desiredDate;

    @NotBlank
    private String projectDescription;

    @NotBlank
    private String bodyPart;

    @Valid
    @NotEmpty
    private List<TattooReferenceDTO> references;

}
