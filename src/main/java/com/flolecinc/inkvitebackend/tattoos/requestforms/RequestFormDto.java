package com.flolecinc.inkvitebackend.tattoos.requestforms;

import com.flolecinc.inkvitebackend.tattoos.clients.TattooClientEntity;
import com.flolecinc.inkvitebackend.tattoos.projects.TattooProjectEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestFormDto {

    @Valid
    @NotNull
    private TattooClientEntity identity;

    @Valid
    @NotNull
    private TattooProjectEntity projectDetails;

}
