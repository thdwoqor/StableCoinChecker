package org.example.stablecoinchecker.infra.cex.coinone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public record Ticker(
        BigDecimal last,
        BigDecimal first,
        BigDecimal low,
        BigDecimal high,
        @JsonProperty("target_volume")
        BigDecimal targetVolume
) {
}
