package com.flolecinc.inkvitebackend.tattoos.requestforms;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
    private ProjectDetailsDto projectDetails;

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

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProjectDetailsDto {

        @NotNull
        private LocalDate desiredDate;

        @NotBlank
        private String projectDescription;

        @NotBlank
        private String bodyPart;

        @Valid
        @NotEmpty
        private List<ReferenceDto> references;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReferenceDto {

        @NotBlank
        private String imagePath;

        @NotBlank
        private String comment;

    }

}
