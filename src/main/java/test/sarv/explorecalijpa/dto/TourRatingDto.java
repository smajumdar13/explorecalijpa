package test.sarv.explorecalijpa.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import test.sarv.explorecalijpa.domain.TourRating;

@Data
public class TourRatingDto {

    @Min(0)
    @Max(5)
    private Integer score;

    @Size(max = 255)
    private String comment;

    @NotNull
    private Integer customerId;

    public TourRatingDto(Integer score, String comment, Integer customerId) {
        this.score = score;
        this.comment = comment;
        this.customerId = customerId;
    }

    public TourRatingDto(TourRating entity) {
        this.score = entity.getScore();
        this.comment = entity.getComment();
        this.customerId = entity.getCustomerId();
    }
}
