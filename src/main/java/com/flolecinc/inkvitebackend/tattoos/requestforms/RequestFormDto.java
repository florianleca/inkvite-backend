package com.flolecinc.inkvitebackend.tattoos.requestforms;

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

    private IdentityDto identity;
    private ProjectDetailsDto projectDetails;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IdentityDto {
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProjectDetailsDto {
        private LocalDate desiredDate;
        private String projectDescription;
        private String bodyPart;
        private List<ReferenceDto> references;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReferenceDto {
        private String imageLink;
        private String comment;
    }

}
