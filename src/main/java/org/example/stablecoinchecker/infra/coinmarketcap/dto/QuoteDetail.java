package org.example.stablecoinchecker.infra.coinmarketcap.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public record QuoteDetail(
        double price,
        @JsonProperty("percent_change_1h")
        double percentChange1h,
        @JsonProperty("percent_change_24h")
        double percentChange24h,
        @JsonProperty("last_updated")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        String lastUpdated
) {
}
