package test.sarv.explorecalijpa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import test.sarv.explorecalijpa.domain.Difficulty;
import test.sarv.explorecalijpa.domain.Region;
import test.sarv.explorecalijpa.service.TourPackageService;
import test.sarv.explorecalijpa.service.TourService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class ExploreCaliJpaApplication implements CommandLineRunner {

    private final String TOUR_IMPORT_FILE = "./src/main/jib/ExploreCalifornia.json";

    @Bean
    public OpenAPI swaggerHeader() {
        return new OpenAPI().info((new Info())
                .description("Services for Explore California API")
                .title("Explore California API")
                .version("1.0.2"));
    }

    @Autowired
    private TourPackageService tourPackageService;

    @Autowired
    private TourService tourService;

    public static void main(String[] args) {
        SpringApplication.run(ExploreCaliJpaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        createTourAllPackages();
//        System.out.println("Persisted packages = " + tourPackageService.total());
//        createToursFromFile(TOUR_IMPORT_FILE);
//        System.out.println("Persisted tours = " + tourService.total());
        System.out.println("Run API tests via: http://localhost:8080/swagger-ui/index.html");
    }

//    private void createTourAllPackages() {
//        tourPackageService.createTourPackage("BC", "Backpack Cal");
//        tourPackageService.createTourPackage("CC", "California Calm");
//        tourPackageService.createTourPackage("CH", "California Hot springs");
//        tourPackageService.createTourPackage("CY", "Cycle California");
//        tourPackageService.createTourPackage("DS", "From Desert to Sea");
//        tourPackageService.createTourPackage("KC", "Kids California");
//        tourPackageService.createTourPackage("NW", "Nature Watch");
//        tourPackageService.createTourPackage("SC", "Snowboard Cali");
//        tourPackageService.createTourPackage("TC", "Taste of California");
//    }

    /**
     * Create tour entities from an external file
     */
//    private void createToursFromFile(String fileToImport) throws IOException {
//        TourFromFile.read(fileToImport).forEach(t ->
//                tourService.createTour(
//                        t.packageName(),
//                        t.title(),
//                        t.description(),
//                        t.blurb(),
//                        t.price(),
//                        t.length(),
//                        t.bullets(),
//                        t.keywords(),
//                        Difficulty.valueOf(t.difficulty()),
//                        Region.findByLabel(t.region())
//                )
//        );
//    }

    /*
     * Helper to import ExploreCali.json
     */
//    record TourFromFile(String packageName, String title, String description,
//                        String blurb, Integer price, String length, String bullets,
//                        String keywords, String difficulty, String region) {
//        static List<TourFromFile> read(String fileToImport) throws IOException {
//            return new ObjectMapper().readValue(new File(fileToImport),
//                    new TypeReference<List<TourFromFile>>() {});
//        }
//    }
}
