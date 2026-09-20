package br.com.devshowcase.api.service;

import br.com.devshowcase.api.dto.FeedbackRequestDTO;
import br.com.devshowcase.api.dto.FeedbackResponseDTO;
import br.com.devshowcase.api.dto.ProjectRequestDTO;
import br.com.devshowcase.api.dto.ProjectResponseDTO;
import br.com.devshowcase.api.entity.Feedback;
import br.com.devshowcase.api.entity.Profile;
import br.com.devshowcase.api.entity.Project;
import br.com.devshowcase.api.entity.Technology;
import br.com.devshowcase.api.exception.ResourceNotFoundException;
import br.com.devshowcase.api.repository.FeedbackRepository;
import br.com.devshowcase.api.repository.ProfileRepository;
import br.com.devshowcase.api.repository.ProjectRepository;
import br.com.devshowcase.api.repository.TechnologyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProfileRepository profileRepository;
    private final TechnologyRepository technologyRepository;
    private final FeedbackRepository feedbackRepository;

    public ProjectService(
        ProjectRepository projectRepository,
        ProfileRepository profileRepository,
        TechnologyRepository technologyRepository,
        FeedbackRepository feedbackRepository
    ) {
        this.projectRepository = projectRepository;
        this.profileRepository = profileRepository;
        this.technologyRepository = technologyRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @Transactional
    public ProjectResponseDTO create(ProjectRequestDTO dto) {
        Profile profile = profileRepository.findById(dto.profileId())
            .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));

        Set<Technology> technologies = new HashSet<>();

        if (dto.technologyIds() != null && !dto.technologyIds().isEmpty()) {
            List<Technology> found = technologyRepository.findAllById(dto.technologyIds());

            if (found.size() != dto.technologyIds().size()) {
                throw new ResourceNotFoundException("Uma ou mais tecnologias não foram encontradas");
            }

            technologies.addAll(found);
        }

        Project project = new Project();
        project.setTitle(dto.title());
        project.setDescription(dto.description());
        project.setRepositoryUrl(dto.repositoryUrl());
        project.setProfile(profile);
        project.setTechnologies(technologies);
        project.setUpvotes(0);
        project.setAverageRating(0.0);

        return ProjectResponseDTO.fromEntity(projectRepository.save(project));
    }

    @Transactional(readOnly = true)
    public Page<ProjectResponseDTO> findAll(String technology, Pageable pageable) {
        Page<Project> projects;

        if (technology == null || technology.isBlank()) {
            projects = projectRepository.findAll(pageable);
        } else {
            projects = projectRepository.findDistinctByTechnologies_NameIgnoreCase(technology.trim(), pageable);
        }

        return projects.map(ProjectResponseDTO::fromEntity);
    }

    @Transactional
    public FeedbackResponseDTO addFeedback(Long projectId, FeedbackRequestDTO dto) {
        Project project = findProject(projectId);

        Feedback feedback = new Feedback();
        feedback.setAuthorName(
            dto.authorName() == null || dto.authorName().isBlank()
                ? "Anônimo"
                : dto.authorName().trim()
        );
        feedback.setRating(dto.rating());
        feedback.setComment(dto.comment().trim());
        feedback.setProject(project);

        Feedback saved = feedbackRepository.saveAndFlush(feedback);

        Double average = feedbackRepository.findAverageRatingByProjectId(projectId);
        project.setAverageRating(average == null ? 0.0 : average);
        projectRepository.save(project);

        return FeedbackResponseDTO.fromEntity(saved, project.getAverageRating());
    }

    @Transactional
    public ProjectResponseDTO upvote(Long projectId) {
        Project project = findProject(projectId);
        project.setUpvotes(project.getUpvotes() + 1);
        return ProjectResponseDTO.fromEntity(projectRepository.save(project));
    }

    private Project findProject(Long projectId) {
        return projectRepository.findById(projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado"));
    }
}
