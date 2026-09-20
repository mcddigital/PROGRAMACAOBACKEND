package br.com.devshowcase.api.dto;

import br.com.devshowcase.api.entity.Profile;

public record ProfileResponseDTO(
    Long id,
    String name,
    String email,
    String bio,
    String githubUrl
) {
    public static ProfileResponseDTO fromEntity(Profile profile) {
        return new ProfileResponseDTO(
            profile.getId(),
            profile.getName(),
            profile.getEmail(),
            profile.getBio(),
            profile.getGithubUrl()
        );
    }
}
