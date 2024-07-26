package test.sarv.explorecalijpa.repository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import test.sarv.explorecalijpa.domain.TourPackage;

import java.util.Optional;

// override /tourPackages url keyword to /packages
@RepositoryRestResource(path = "packages", collectionResourceRel = "packages")
@Tag(name = "Tour Package Repository")
public interface TourPackageRepository extends JpaRepository<TourPackage, String> {

    @Operation(summary = "Lookup by name")
    Optional<TourPackage> findByName(String name);
}
