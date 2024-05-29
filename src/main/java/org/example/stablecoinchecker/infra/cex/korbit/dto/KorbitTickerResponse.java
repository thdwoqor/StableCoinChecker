package org.example.stablecoinchecker.infra.cex.korbit.dto;

import org.example.stablecoinchecker.infra.cex.TickerResponse;

public record KorbitTickerResponse(
        String last,
        String open,
        String low,
        String high,
        String volume
) {
    public TickerResponse toTickerResponse(final String exchangeName, final String orderCurrency) {
        return new TickerResponse(
                exchangeName,
                orderCurrency.toUpperCase(),
                last,
                open,
                low,
                high,
                volume
        );
    }
}
