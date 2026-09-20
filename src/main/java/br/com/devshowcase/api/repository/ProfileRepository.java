package br.com.devshowcase.api.repository;

import br.com.devshowcase.api.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
