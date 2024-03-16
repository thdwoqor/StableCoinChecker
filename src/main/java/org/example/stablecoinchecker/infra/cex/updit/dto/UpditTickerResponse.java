package org.example.stablecoinchecker.infra.cex.updit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpditTickerResponse(
        @JsonProperty("trade_price")
        double tradePrice
) {
}
