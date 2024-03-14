package org.example.stablecoinchecker.infra.coinmarketcap.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Token(
        String symbol,
        @JsonProperty("last_updated")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        String lastUpdated,
        Quote quote
) {
}
