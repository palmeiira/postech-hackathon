package br.com.postech.fiap.telemedicine.service;

import br.com.postech.fiap.telemedicine.entities.Rating;
import br.com.postech.fiap.telemedicine.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;

    public Rating registerRating(Rating rating) {
        return ratingRepository.save(rating);
    }

}
