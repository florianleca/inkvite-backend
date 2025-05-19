package com.flolecinc.inkvitebackend.tattoos.requestforms;

import com.flolecinc.inkvitebackend.tattoos.projects.TattooProjectEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    private IdentityDto identity;

    @Valid
    @NotNull
    private TattooProjectEntity projectDetails;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IdentityDto {

        @NotBlank
        private String firstName;

        @NotBlank
        private String lastName;

        @Email
        @NotBlank
        private String email;

    }

}
