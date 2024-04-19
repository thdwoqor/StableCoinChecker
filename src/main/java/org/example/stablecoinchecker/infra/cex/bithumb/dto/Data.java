package org.example.stablecoinchecker.infra.cex.bithumb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Data(

        @JsonProperty("closing_price")
        String closingPrice,
        @JsonProperty("opening_price")
        String openingPrice,
        @JsonProperty("min_price")
        String minPrice,
        @JsonProperty("max_price")
        String maxPrice
) {
}
