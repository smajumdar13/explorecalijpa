package test.sarv.explorecalijpa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import test.sarv.explorecalijpa.domain.TourRating;
import test.sarv.explorecalijpa.dto.TourRatingDto;
import test.sarv.explorecalijpa.service.TourRatingService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@Tag(name = "Tour Rating", description = "Manage tour ratings")
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {
    private TourRatingService tourRatingService;

    public TourRatingController(TourRatingService tourRatingService) {
        this.tourRatingService = tourRatingService;
    }

    @PostMapping
    @Operation(summary = "Create new tour rating")
    @ResponseStatus(HttpStatus.CREATED)
    public TourRatingDto createTourRating(@PathVariable(value = "tourId") int tourId, @RequestBody @Valid TourRatingDto ratingDto) {
        log.info("POST /tours/{}/ratings", tourId);
        TourRating rating = tourRatingService.createNew(tourId, ratingDto.getCustomerId(), ratingDto.getScore(), ratingDto.getComment());
        return new TourRatingDto(rating);
    }

    @GetMapping
    @Operation(summary = "Lookup all ratings for a tour")
    public List<TourRatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId) {
        List<TourRating> tourRatings = tourRatingService.lookupRatings(tourId);
        log.info("GET /tours/{}/ratings", tourId);
        return tourRatings.stream()
                .map(rating -> new TourRatingDto(rating.getScore(), rating.getComment(), rating.getCustomerId()))
                .toList();
    }

    @GetMapping(path = "/average")
    @Operation(summary = "Get average tour rating")
    public Map<String, Double> getAverageRatingForTour(@PathVariable(value = "tourId") int tourId) {
//        return Map.of("average", new DecimalFormat("0.00").format(tourRatingService.getAverageScore(tourId)));
        log.info("GET /tours/{}/ratings/average", tourId);
        return Map.of("average", tourRatingService.getAverageScore(tourId));
    }

    @PutMapping
    @Operation(summary = "Modify tour rating attributes")
    public TourRatingDto updateWithPut(@PathVariable(value = "tourId") int tourId, @RequestBody @Valid TourRatingDto ratingDto) {
        log.warn("PUT /tours/{}/ratings", tourId);
        return new TourRatingDto(tourRatingService.update(tourId,
                                                          ratingDto.getCustomerId(),
                                                          ratingDto.getScore(),
                                                          ratingDto.getComment()));
    }

    @PatchMapping
    @Operation(summary = "Update some tour rating attributes")
    public TourRatingDto updateWithPatch(@PathVariable(value = "tourId") int tourId, @RequestBody @Valid TourRatingDto ratingDto) {
        log.warn("PATCH /tours/{}/ratings", tourId);
        return new TourRatingDto(tourRatingService.updateSome(tourId,
                                                              ratingDto.getCustomerId(),
                                                              Optional.ofNullable(ratingDto.getScore()),
                                                              Optional.ofNullable(ratingDto.getComment())));
    }

    @DeleteMapping("{customerId}")
    @Operation(summary = "Delete tour rating")
    public void delete(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "customerId") int customerId) {
        log.warn("DELETE /tours/{}/ratings/{}", tourId, customerId);
        tourRatingService.deleteRating(tourId, customerId);
    }

    @PostMapping("/batch")
    @Operation(summary = "Batch add multiple tour ratings")
    @ResponseStatus(HttpStatus.CREATED)
    public void createManyTourRatings(@PathVariable(value = "tourId") int tourId,
                                      @RequestParam(value = "score") int score,
                                      @RequestBody List<Integer> customers) {
        log.info("POST /tours/{}/ratings/batch", tourId);
        tourRatingService.rateMany(tourId, score, customers);
    }

//    @ExceptionHandler(NoSuchElementException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String return404(NoSuchElementException ex) {
//        return ex.getMessage();
//    }

}
