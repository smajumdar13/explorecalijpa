package test.sarv.explorecalijpa.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import test.sarv.explorecalijpa.domain.Difficulty;
import test.sarv.explorecalijpa.domain.Region;
import test.sarv.explorecalijpa.domain.Tour;
import test.sarv.explorecalijpa.domain.TourPackage;
import test.sarv.explorecalijpa.repository.TourPackageRepository;
import test.sarv.explorecalijpa.repository.TourRepository;

import java.util.List;

@Service
@Slf4j
@Transactional
public class TourService {
    private TourPackageRepository tourPackageRepository;
    private TourRepository tourRepository;

    public TourService(TourPackageRepository tourPackageRepository, TourRepository tourRepository) {
        this.tourPackageRepository = tourPackageRepository;
        this.tourRepository = tourRepository;
    }

    public Tour createTour(String tourPackageName, String title, String description, String blurb, Integer price,
                           String duration, String bullets, String keywords, Difficulty difficulty, Region region) {

        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName)
                .orElseThrow(() -> new RuntimeException("Tour package not found for name: " + tourPackageName));
        log.info("Creating tour {} in tour package {}", title, tourPackageName);
        return tourRepository.save(new Tour(title, description, blurb, price, duration, bullets, keywords, tourPackage, difficulty, region));
    }

    public List<Tour> lookupByDifficulty(Difficulty difficulty) {
        log.info("Looking up tours with difficulty {}", difficulty);
        return tourRepository.findByDifficulty(difficulty);
    }

    public List<Tour> lookupByPackage(String tourPackageCode) {
        log.info("Looking up tours in tour package {}", tourPackageCode);
        return tourRepository.findByTourPackageCode(tourPackageCode);
    }

    public long total() {
        log.info("Counting all tours");
        return tourRepository.count();
    }
}
