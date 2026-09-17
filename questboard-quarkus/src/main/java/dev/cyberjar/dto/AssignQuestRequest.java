package dev.cyberjar.dto;

import jakarta.validation.constraints.NotBlank;

public record AssignQuestRequest(
        @NotBlank String heroName,
        @NotBlank String heroClass
) {
}
