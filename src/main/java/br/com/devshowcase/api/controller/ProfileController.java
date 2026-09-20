package br.com.devshowcase.api.controller;

import br.com.devshowcase.api.dto.ProfileRequestDTO;
import br.com.devshowcase.api.dto.ProfileResponseDTO;
import br.com.devshowcase.api.entity.Profile;
import br.com.devshowcase.api.repository.ProfileRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileRepository profileRepository;

    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponseDTO create(@Valid @RequestBody ProfileRequestDTO dto) {
        Profile profile = new Profile();
        profile.setName(dto.name());
        profile.setEmail(dto.email());
        profile.setBio(dto.bio());
        profile.setGithubUrl(dto.githubUrl());

        return ProfileResponseDTO.fromEntity(profileRepository.save(profile));
    }

    @GetMapping("/{id}")
    public ProfileResponseDTO findById(@PathVariable Long id) {
        Profile profile = profileRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Perfil não encontrado"
            ));

        return ProfileResponseDTO.fromEntity(profile);
    }
}
