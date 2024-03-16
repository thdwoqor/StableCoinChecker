package org.example.stablecoinchecker.infra.cex.bithumb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExchangeData(
        @JsonProperty("closing_price")
        String closingPrice
) {
}
