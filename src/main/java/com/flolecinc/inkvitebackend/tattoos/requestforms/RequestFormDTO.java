package com.flolecinc.inkvitebackend.tattoos.requestforms;

import com.flolecinc.inkvitebackend.tattoos.clients.TattooClientDTO;
import com.flolecinc.inkvitebackend.tattoos.projects.TattooProjectDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestFormDTO {

    @Valid
    @NotNull
    private TattooClientDTO identity;

    @Valid
    @NotNull
    private TattooProjectDTO projectDetails;

}
