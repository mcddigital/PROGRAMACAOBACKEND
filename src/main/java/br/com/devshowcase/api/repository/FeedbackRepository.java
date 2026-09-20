package br.com.devshowcase.api.repository;

import br.com.devshowcase.api.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("select avg(f.rating) from Feedback f where f.project.id = :projectId")
    Double findAverageRatingByProjectId(@Param("projectId") Long projectId);
}
