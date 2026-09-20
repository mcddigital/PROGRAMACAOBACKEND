package br.com.devshowcase.api.controller;

import br.com.devshowcase.api.dto.FeedbackRequestDTO;
import br.com.devshowcase.api.dto.FeedbackResponseDTO;
import br.com.devshowcase.api.dto.PageResponseDTO;
import br.com.devshowcase.api.dto.ProjectRequestDTO;
import br.com.devshowcase.api.dto.ProjectResponseDTO;
import br.com.devshowcase.api.service.ProjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@Validated
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponseDTO create(@Valid @RequestBody ProjectRequestDTO dto) {
        return projectService.create(dto);
    }

    @GetMapping
    public PageResponseDTO<ProjectResponseDTO> findAll(
        @RequestParam(required = false) String technology,
        @RequestParam(defaultValue = "0") @Min(value = 0, message = "A página deve ser 0 ou maior") int page,
        @RequestParam(defaultValue = "10")
        @Min(value = 1, message = "O tamanho da página deve ser no mínimo 1")
        @Max(value = 100, message = "O tamanho da página deve ser no máximo 100") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return PageResponseDTO.from(projectService.findAll(technology, pageable));
    }

    @PostMapping("/{id}/feedbacks")
    @ResponseStatus(HttpStatus.CREATED)
    public FeedbackResponseDTO addFeedback(
        @PathVariable Long id,
        @Valid @RequestBody FeedbackRequestDTO dto
    ) {
        return projectService.addFeedback(id, dto);
    }

    @PutMapping("/{id}/upvote")
    public ProjectResponseDTO upvote(@PathVariable Long id) {
        return projectService.upvote(id);
    }
}
