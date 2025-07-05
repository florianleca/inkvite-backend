package com.flolecinc.inkvitebackend.tattoos.references.images;

import jakarta.validation.constraints.NotBlank;

public record TempImageDTO(@NotBlank String imagePath, @NotBlank String imageUrl) {
}
