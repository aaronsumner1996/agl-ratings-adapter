package com.agl.ratings.persistence;

import com.agl.ratings.persistence.entity.RatingsEntity;
import io.micronaut.transaction.annotation.TransactionalAdvice;

import javax.transaction.Transactional;

@Transactional
public class RatingsDao extends AbstractDao {

    @TransactionalAdvice
    public RatingsEntity findRatingForUser(final String gameSlug, final String userId) throws Exception {
        return findOne("SELECT ratings FROM RatingsEntity ratings WHERE ratings.gameSlug=:gameSlug AND " +
                        "ratings.userId=:userId",
                RatingsEntity.class,
                "gameSlug", gameSlug, "userId", userId);
    }

    @TransactionalAdvice
    public Double getAverageRating(final String gameSlug) throws Exception {
        return findOne("SELECT AVG(rating) FROM RatingsEntity ratings WHERE ratings.gameSlug=:gameSlug",
                Double.class,
                "gameSlug", gameSlug);
    }

    @TransactionalAdvice
    @Transactional
    public RatingsEntity create(final RatingsEntity ratingsEntity) {
        entityManager.persist(ratingsEntity);
        return ratingsEntity;
    }


    @TransactionalAdvice
    @Transactional
    public RatingsEntity update(final RatingsEntity ratingsEntity) {
        entityManager.merge(ratingsEntity);
        return ratingsEntity;
    }

}
