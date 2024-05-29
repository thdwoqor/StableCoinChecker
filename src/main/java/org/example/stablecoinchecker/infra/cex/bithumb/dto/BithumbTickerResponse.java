package org.example.stablecoinchecker.infra.cex.bithumb.dto;

import org.example.stablecoinchecker.infra.cex.TickerResponse;

public record BithumbTickerResponse(
        String status,
        Data data
) {

    public TickerResponse toStableCoinTicker(final String exchangeName, final String orderCurrency) {
        return new TickerResponse(
                exchangeName,
                orderCurrency.toUpperCase(),
                data.closingPrice(),
                data.openingPrice(),
                data.minPrice(),
                data.maxPrice(),
                data.unitsTraded()
        );
    }
}
