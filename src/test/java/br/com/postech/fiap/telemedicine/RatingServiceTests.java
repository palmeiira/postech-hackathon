package br.com.postech.fiap.telemedicine;

import br.com.postech.fiap.telemedicine.entities.Rating;
import br.com.postech.fiap.telemedicine.entities.Slot;
import br.com.postech.fiap.telemedicine.repository.RatingRepository;
import br.com.postech.fiap.telemedicine.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RatingServiceTests {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        Rating r = new Rating();
        r.setId(1L);
        r.setRating(3.3);

        when(ratingRepository.save(r))
                .thenReturn(r);

        Rating result = ratingService.registerRating(r);

        assertEquals(result.getId(), r.getId());
    }

}
