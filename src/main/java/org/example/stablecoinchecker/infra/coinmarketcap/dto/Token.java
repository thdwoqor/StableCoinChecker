package org.example.stablecoinchecker.infra.coinmarketcap.dto;

public record Token(
        String symbol,
        Quote quote
) {
}
