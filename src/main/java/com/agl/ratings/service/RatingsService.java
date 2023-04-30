package com.agl.ratings.service;

import com.agl.ratings.controller.model.RatingsSumResponse;
import com.agl.ratings.persistence.RatingsDao;
import com.agl.ratings.persistence.entity.RatingsEntity;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.UUID;


@Singleton
@Slf4j
public class RatingsService {

    private final RatingsDao ratingsDao;

    @Inject
    public RatingsService(RatingsDao ratingsDao) {
        this.ratingsDao = ratingsDao;
    }

    public void addReview(String userId, String gameSlug, BigDecimal rating) {

        log.info("Checking Rating Format");
        checkRatingFormat(rating);

        RatingsEntity ratings = getRatingsEntity(userId, gameSlug, rating);

        log.info("Checking if rating exists for User");
        if (checkForExistingRating(ratings)) {
            log.info("Rating Updated Successfully");
        } else {
            ratingsDao.create(ratings);
        }
    }


    private RatingsEntity getRatingsEntity(String userId, String gameSlug, BigDecimal rating) {
        return RatingsEntity.builder()
                .id(UUID.randomUUID().toString())
                .gameSlug(gameSlug)
                .userId(userId)
                .rating(rating)
                .build();
    }

    private void checkRatingFormat(BigDecimal rating) {
        if ((rating.compareTo(BigDecimal.valueOf(5)) > 0) || (rating.compareTo(BigDecimal.ZERO) <= 0)) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Rating is not in range of 0-5");
        }
    }


    private boolean checkForExistingRating(RatingsEntity ratings) {
        try {
            RatingsEntity ratingsEntity = ratingsDao.findRatingForUser(ratings.getGameSlug(), ratings.getUserId());
            if (!Objects.isNull(ratingsEntity)) {
                log.info("rating already exists merging new rating : {}", ratingsEntity);

                RatingsEntity newRating = RatingsEntity.builder()
                        .id(ratingsEntity.getId())
                        .gameSlug(ratingsEntity.getGameSlug())
                        .userId(ratingsEntity.getUserId())
                        .rating(ratings.getRating())
                        .build();

                ratingsDao.update(newRating);
               return true;
            } else {
                log.info("Rating does not exist");
                return false;
            }
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            throw new HttpStatusException(HttpStatus.BAD_GATEWAY, "Could not save rating to DB");
        }
    }

    public BigDecimal retrieveGameRating(String gameSlug) {
        try {
            Double rating = ratingsDao.getAverageRating(gameSlug);
            return BigDecimal.valueOf(rating).setScale(1, RoundingMode.HALF_UP);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            throw new HttpStatusException(HttpStatus.BAD_GATEWAY, "Could not fetch Rating: [{}]");
        }
    }
}
