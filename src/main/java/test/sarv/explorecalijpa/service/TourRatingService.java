package test.sarv.explorecalijpa.service;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import test.sarv.explorecalijpa.domain.Tour;
import test.sarv.explorecalijpa.domain.TourRating;
import test.sarv.explorecalijpa.repository.TourRatingRepository;
import test.sarv.explorecalijpa.repository.TourRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
@Slf4j
@Transactional
public class TourRatingService {
    private TourRatingRepository tourRatingRepository;
    private TourRepository tourRepository;

    public TourRatingService(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    public TourRating createNew(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
        log.info("Creating new tour rating for tour {} and customer {}", tourId, customerId);
        return tourRatingRepository.save(new TourRating(verifyTour(tourId), customerId, score, comment));
    }

    public void rateMany(int tourId, int score, List<Integer> customers) {
        Tour tour = verifyTour(tourId);
        log.info("Rating tour {} with score {} for customers {}", tourId, score, customers);
        for (Integer customerId : customers) {
            if (tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId).isPresent()) {
                throw new ConstraintViolationException("Unable to create duplicate ratings", null);
            }
            tourRatingRepository.save(new TourRating(tour, customerId, score));
        }
    }

    public List<TourRating> lookupAll() {
        log.info("Looking up all tour ratings");
        return tourRatingRepository.findAll();
    }

    public Optional<TourRating> lookupRatingById(int id) {
        log.info("Looking up tour rating with id {}", id);
        return tourRatingRepository.findById(id);
    }

    public List<TourRating> lookupRatings(int tourId) throws NoSuchElementException{
        log.info("Looking up tour ratings for tour {}", tourId);
        return tourRatingRepository.findByTourId(verifyTour(tourId).getId());
    }

    public TourRating update(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException{
        TourRating rating = verifyTourRating(tourId, customerId);
        rating.setScore(score);
        rating.setComment(comment);
        log.info("Updating tour rating for tour {} and customer {}", tourId, customerId);
        return tourRatingRepository.save(rating);
    }

    public TourRating updateSome(int tourId, Integer customerId, Optional<Integer> score, Optional<String> comment) throws NoSuchElementException {
        TourRating rating = verifyTourRating(tourId, customerId);
        score.ifPresent(rating::setScore);
        comment.ifPresent(rating::setComment);
        log.info("Updating tour rating for tour {} and customer {}", tourId, customerId);
        return tourRatingRepository.save(rating);
    }

    public void deleteRating(int tourId, Integer customerId) throws NoSuchElementException {
        TourRating rating = verifyTourRating(tourId, customerId);
        log.info("Deleting tour rating for tour {} and customer {}", tourId, customerId);
        tourRatingRepository.delete(rating);
    }

    public Double getAverageScore(int tourId) {
        List<TourRating> ratings = tourRatingRepository.findByTourId(verifyTour(tourId).getId());
        OptionalDouble average = ratings.stream().mapToInt(TourRating::getScore).average();
        log.info("Average score for tour {} is {}", tourId, average);
        return average.orElse(0.0);
    }

    private Tour verifyTour(int tourId) {
        log.info("Verifying tour with id {}", tourId);
        return tourRepository
                .findById(tourId)
                .orElseThrow(() -> new NoSuchElementException("Tour not found for id: " + tourId));
    }

    public TourRating verifyTourRating(int tourId, Integer customerId) throws NoSuchElementException {
        log.info("Verifying tour rating for tour {} and customer {}", tourId, customerId);
        return tourRatingRepository
                .findByTourIdAndCustomerId(tourId, customerId)
                .orElseThrow(() -> new NoSuchElementException("Tour rating not found for tour id: " + tourId + " and customer id: " + customerId));
    }

}
