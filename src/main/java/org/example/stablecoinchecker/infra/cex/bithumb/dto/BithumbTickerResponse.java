package org.example.stablecoinchecker.infra.cex.bithumb.dto;

public record BithumbTickerResponse(
        String status,
        ExchangeData data
) {
}
