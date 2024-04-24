package org.example.stablecoinchecker.infra.cex;

public record StableCoinTickerResponse(
        String cex,
        String symbol,
        String close,
        String open,
        String low,
        String high
) {
}
