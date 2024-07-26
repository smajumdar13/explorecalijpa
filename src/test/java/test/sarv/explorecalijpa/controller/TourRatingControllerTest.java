package test.sarv.explorecalijpa.controller;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import test.sarv.explorecalijpa.domain.Tour;
import test.sarv.explorecalijpa.domain.TourRating;
import test.sarv.explorecalijpa.dto.TourRatingDto;
import test.sarv.explorecalijpa.service.TourRatingService;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class TourRatingControllerTest {

    private static final int TOUR_ID = 999;
    private static final int CUSTOMER_ID = 1000;
    private static final int SCORE = 3;
    private static final String COMMENT = "comment";
    private static final String TOUR_RATINGS_URL = "/tours/" + TOUR_ID + "/ratings";

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private TourRatingService serviceMock;

    @Mock
    private TourRating tourRatingMock;

    @Mock
    private Tour tourMock;

    private TourRatingDto ratingDto = new TourRatingDto(SCORE, COMMENT,CUSTOMER_ID);

    @Test
    void testCreateTourRating() {

        restTemplate.postForEntity(TOUR_RATINGS_URL, ratingDto, TourRatingDto.class);

        verify(this.serviceMock).createNew(TOUR_ID, CUSTOMER_ID, SCORE, COMMENT);
    }

    @Test
    void testDelete() {

        restTemplate.delete(TOUR_RATINGS_URL + "/" + CUSTOMER_ID);

        verify(this.serviceMock).deleteRating(TOUR_ID, CUSTOMER_ID);
    }

    @Test
    void testGetAllRatingsForTour() {
        when(serviceMock.lookupRatings(anyInt())).thenReturn(List.of(tourRatingMock));
        ResponseEntity<String> res = restTemplate.getForEntity(TOUR_RATINGS_URL, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(serviceMock).lookupRatings(anyInt());
    }

    @Test
    void testGetAverage() {
        when(serviceMock.lookupRatings(anyInt())).thenReturn(List.of(tourRatingMock));
        ResponseEntity<String> res = restTemplate.getForEntity(TOUR_RATINGS_URL + "/average", String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(serviceMock).getAverageScore(TOUR_ID);
    }

    /*
     * PATCH testing only works when adding http client dependency to pom.xml
     */
    @Test
    void testUpdateWithPatch() {
        when(serviceMock.updateSome(anyInt(), anyInt(), any(), any())).thenReturn(tourRatingMock);

        restTemplate.patchForObject(TOUR_RATINGS_URL, ratingDto, String.class);
        verify(this.serviceMock).updateSome(anyInt(), anyInt(), any(), any());
    }

    @Test
    void testUpdateWithPut() {
        restTemplate.put(TOUR_RATINGS_URL, ratingDto);

        verify(this.serviceMock).update(TOUR_ID, CUSTOMER_ID, SCORE, COMMENT);
    }

    @Test
    void testCreateManyTourRatings() {
        Integer customers[] = {123};
        restTemplate.postForObject(TOUR_RATINGS_URL + "/batch?score=" + SCORE, customers,
                String.class);

        verify(this.serviceMock).rateMany(anyInt(), anyInt(), anyList());
    }

    /** Test unhappy Paths too to validate GlobalExceptionHandler */

    @Test
    public void test404() {
        when(serviceMock.lookupRatings(anyInt())).thenThrow(new NoSuchElementException());
        ResponseEntity<String> res = restTemplate.getForEntity(TOUR_RATINGS_URL, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void test400() {
        when(serviceMock.lookupRatings(anyInt())).thenThrow(new ConstraintViolationException(null));
        ResponseEntity<String> res = restTemplate.getForEntity(TOUR_RATINGS_URL, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}