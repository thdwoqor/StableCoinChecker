package org.example.stablecoinchecker.infra.cex.coinone.dto;

public record Ticker(
        String last,
        String first,
        String low,
        String high
) {
}
