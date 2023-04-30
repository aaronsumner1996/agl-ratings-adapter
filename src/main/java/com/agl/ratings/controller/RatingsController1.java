package com.agl.ratings.controller;

import com.agl.ratings.controller.model.RatingsRequest;
import com.agl.ratings.service.RatingsService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

import static io.micronaut.http.MediaType.APPLICATION_JSON;

@Controller
@Slf4j
public class RatingsController1 {

    private final RatingsService ratingsService;

    @Inject
    public RatingsController1(RatingsService ratingsService) {
        this.ratingsService = ratingsService;
    }

    @Put(value = "/api/games/ratings", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse<?> addReview(@Body RatingsRequest ratingsRequest) {
        ratingsService.addReview(ratingsRequest.getUserId(), ratingsRequest.getGameSlug(), ratingsRequest.getRating());
        log.info("addReview, adding review for user: [{}] ", ratingsRequest.getUserId());
        return HttpResponse.noContent();
    }

    @Get(value = "/api/games/ratings/{gameSlug}", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    @Consumes(APPLICATION_JSON)
    public HttpResponse<BigDecimal> retrieveGameRating(@PathVariable(value = "gameSlug") String gameSlug) {
        log.info("retrieveGameInfo, Retrieving : [{}]" , gameSlug);
        return HttpResponse.ok(ratingsService.retrieveGameRating(gameSlug));
    }

}
