package org.example.stablecoinchecker.infra.exchange.bithumb.dto;

public record BithumbTickerResponse(
        String status,
        ExchangeData data
) {
}
