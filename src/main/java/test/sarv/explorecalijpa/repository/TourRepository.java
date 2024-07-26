package test.sarv.explorecalijpa.repository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import test.sarv.explorecalijpa.domain.Difficulty;
import test.sarv.explorecalijpa.domain.Tour;

import java.util.List;

@Tag(name = "Tour Repository")
public interface TourRepository extends JpaRepository<Tour, Integer> {

    @Operation(summary = "Lookup by Difficulty")
    List<Tour> findByDifficulty(Difficulty difficulty);

    @Operation(summary = "Lookup by Code")
    List<Tour> findByTourPackageCode(String tourPackageCode);
}
