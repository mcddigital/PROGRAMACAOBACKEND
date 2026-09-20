package br.com.devshowcase.api.dto;

import br.com.devshowcase.api.entity.Feedback;

public record FeedbackResponseDTO(
    Long id,
    String authorName,
    Integer rating,
    String comment,
    Long projectId,
    Double projectAverageRating
) {
    public static FeedbackResponseDTO fromEntity(Feedback feedback, Double projectAverageRating) {
        return new FeedbackResponseDTO(
            feedback.getId(),
            feedback.getAuthorName(),
            feedback.getRating(),
            feedback.getComment(),
            feedback.getProject().getId(),
            projectAverageRating
        );
    }
}
