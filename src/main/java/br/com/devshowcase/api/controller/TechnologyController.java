package br.com.devshowcase.api.controller;

import br.com.devshowcase.api.dto.TechnologyRequestDTO;
import br.com.devshowcase.api.dto.TechnologyResponseDTO;
import br.com.devshowcase.api.entity.Technology;
import br.com.devshowcase.api.repository.TechnologyRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/technologies")
public class TechnologyController {

    private final TechnologyRepository technologyRepository;

    public TechnologyController(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TechnologyResponseDTO create(@Valid @RequestBody TechnologyRequestDTO dto) {
        Technology technology = new Technology();
        technology.setName(dto.name());

        return TechnologyResponseDTO.fromEntity(technologyRepository.save(technology));
    }

    @GetMapping
    public List<TechnologyResponseDTO> findAll() {
        return technologyRepository.findAll()
            .stream()
            .map(TechnologyResponseDTO::fromEntity)
            .toList();
    }
}
