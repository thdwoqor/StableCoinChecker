package org.example.stablecoinchecker.service.dto;

public record StableCoinSearchCondition(
        String cex,
        String symbol,
        Long interval,
        Long limit,
        Long to
) {
}
