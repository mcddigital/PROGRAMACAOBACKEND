package br.com.devshowcase.api.dto;

import br.com.devshowcase.api.entity.Project;
import java.util.Set;
import java.util.stream.Collectors;

public record ProjectResponseDTO(
    Long id,
    String title,
    String description,
    String repositoryUrl,
    Long profileId,
    Integer upvotes,
    Double averageRating,
    Set<TechnologyResponseDTO> technologies
) {
    public static ProjectResponseDTO fromEntity(Project project) {
        return new ProjectResponseDTO(
            project.getId(),
            project.getTitle(),
            project.getDescription(),
            project.getRepositoryUrl(),
            project.getProfile().getId(),
            project.getUpvotes(),
            project.getAverageRating(),
            project.getTechnologies()
                .stream()
                .map(TechnologyResponseDTO::fromEntity)
                .collect(Collectors.toSet())
        );
    }
}
