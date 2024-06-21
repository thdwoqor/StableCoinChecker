package org.example.stablecoinchecker.infra.cex.bithumb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public record Data(

        @JsonProperty("closing_price")
        BigDecimal closingPrice,
        @JsonProperty("opening_price")
        BigDecimal openingPrice,
        @JsonProperty("min_price")
        BigDecimal minPrice,
        @JsonProperty("max_price")
        BigDecimal maxPrice,
        @JsonProperty("units_traded")
        BigDecimal unitsTraded
) {
}
