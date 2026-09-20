package br.com.devshowcase.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FeedbackRequestDTO(
    String authorName,

    @NotNull(message = "A nota é obrigatória")
    @Min(value = 1, message = "A nota deve ser no mínimo 1")
    @Max(value = 5, message = "A nota deve ser no máximo 5")
    Integer rating,

    @NotBlank(message = "O comentário é obrigatório")
    String comment
) {}
