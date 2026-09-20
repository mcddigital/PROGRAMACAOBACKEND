package br.com.devshowcase.api.dto;

import br.com.devshowcase.api.entity.Technology;

public record TechnologyResponseDTO(
    Long id,
    String name
) {
    public static TechnologyResponseDTO fromEntity(Technology technology) {
        return new TechnologyResponseDTO(
            technology.getId(),
            technology.getName()
        );
    }
}
