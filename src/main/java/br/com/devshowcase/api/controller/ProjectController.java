package br.com.devshowcase.api.controller;

import br.com.devshowcase.api.dto.ProjectRequestDTO;
import br.com.devshowcase.api.dto.ProjectResponseDTO;
import br.com.devshowcase.api.entity.Profile;
import br.com.devshowcase.api.entity.Project;
import br.com.devshowcase.api.entity.Technology;
import br.com.devshowcase.api.repository.ProfileRepository;
import br.com.devshowcase.api.repository.ProjectRepository;
import br.com.devshowcase.api.repository.TechnologyRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final ProfileRepository profileRepository;
    private final TechnologyRepository technologyRepository;

    public ProjectController(
        ProjectRepository projectRepository,
        ProfileRepository profileRepository,
        TechnologyRepository technologyRepository
    ) {
        this.projectRepository = projectRepository;
        this.profileRepository = profileRepository;
        this.technologyRepository = technologyRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponseDTO create(@Valid @RequestBody ProjectRequestDTO dto) {
        Profile profile = profileRepository.findById(dto.profileId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Perfil não encontrado"
            ));

        Set<Technology> technologies = new HashSet<>();

        if (dto.technologyIds() != null && !dto.technologyIds().isEmpty()) {
            List<Technology> found = technologyRepository.findAllById(dto.technologyIds());

            if (found.size() != dto.technologyIds().size()) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Uma ou mais tecnologias não foram encontradas"
                );
            }

            technologies.addAll(found);
        }

        Project project = new Project();
        project.setTitle(dto.title());
        project.setDescription(dto.description());
        project.setRepositoryUrl(dto.repositoryUrl());
        project.setProfile(profile);
        project.setTechnologies(technologies);

        return ProjectResponseDTO.fromEntity(projectRepository.save(project));
    }

    @GetMapping
    public List<ProjectResponseDTO> findAll() {
        return projectRepository.findAll()
            .stream()
            .map(ProjectResponseDTO::fromEntity)
            .toList();
    }
}
