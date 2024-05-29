package org.example.stablecoinchecker.infra.cex;

public record TickerResponse(
        String cex,
        String symbol,
        String close,
        String open,
        String low,
        String high,
        String volume
) {
}
