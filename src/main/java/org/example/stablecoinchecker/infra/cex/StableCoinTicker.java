package org.example.stablecoinchecker.infra.cex;

public record StableCoinTicker(
        CryptocurrencyExchange cex,
        String symbol,
        String close,
        String open,
        String low,
        String high
) {
}
