package test.sarv.explorecalijpa.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import test.sarv.explorecalijpa.domain.TourPackage;
import test.sarv.explorecalijpa.repository.TourPackageRepository;

import java.util.List;

@Service
@Slf4j
@Transactional
public class TourPackageService {
    private TourPackageRepository tourPackageRepository;

    public TourPackageService(TourPackageRepository tourPackageRepository) {
        this.tourPackageRepository = tourPackageRepository;
    }

    public TourPackage createTourPackage(String code, String name) {
        log.info("Creating tour package {} with code {}", name, code);
        return tourPackageRepository.findById(code)
                .orElse(tourPackageRepository.save(new TourPackage(code, name)));
    }

    public List<TourPackage> listAll() {
        log.info("Listing all tour packages");
        return tourPackageRepository.findAll();
    }

    public long total() {
        log.info("Counting all tour packages");
        return tourPackageRepository.count();
    }

}
