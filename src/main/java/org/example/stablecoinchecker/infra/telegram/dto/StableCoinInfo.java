package org.example.stablecoinchecker.infra.telegram.dto;

public record StableCoinInfo(
        String cex,
        String symbol,
        int price,
        double kimchiPremium
) {
}
