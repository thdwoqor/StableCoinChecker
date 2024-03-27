package org.example.stablecoinchecker.infra.cex;

public record StableCoinTicker(
        CryptocurrencyExchange cex,
        String symbol,
        String price
) {
}
