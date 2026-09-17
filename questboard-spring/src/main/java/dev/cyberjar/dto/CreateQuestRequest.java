package dev.cyberjar.dto;

import dev.cyberjar.domain.Difficulty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateQuestRequest(
        @NotBlank String title,
        @NotNull Difficulty difficulty,
        @Min(1) int reward,
        @NotBlank String requiredClass
) {
}
