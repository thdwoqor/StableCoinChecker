package org.example.stablecoinchecker.infra.exchange;

public record StableCoinResponse(
        CryptocurrencyExchange cex,
        Symbol symbol,
        String price
) {
}
