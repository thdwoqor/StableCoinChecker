package org.example.stablecoinchecker.infra.bithumb.dto;

public record BithumbPriceResponse(
        String status,
        ExchangeData data
) {
}
