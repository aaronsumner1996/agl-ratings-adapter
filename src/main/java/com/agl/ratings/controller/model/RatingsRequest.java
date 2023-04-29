package com.agl.ratings.controller.model;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class RatingsRequest {

    private String userId;

    private String gameSlug;

    private BigDecimal rating;

}
