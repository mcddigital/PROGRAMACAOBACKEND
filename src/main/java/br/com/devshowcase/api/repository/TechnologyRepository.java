package br.com.devshowcase.api.repository;

import br.com.devshowcase.api.entity.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {
}
