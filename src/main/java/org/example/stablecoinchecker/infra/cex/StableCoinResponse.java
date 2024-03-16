package org.example.stablecoinchecker.infra.cex;

public record StableCoinResponse(
        CryptocurrencyExchange cex,
        Symbol symbol,
        String price
) {
}
