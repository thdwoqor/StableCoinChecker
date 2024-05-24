package org.example.stablecoinchecker.infra.cex.coinone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Ticker(
        String last,
        String first,
        String low,
        String high,
        @JsonProperty("target_volume")
        String targetVolume
) {
}
