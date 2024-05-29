package org.example.stablecoinchecker.infra.cex.gopax.dto;

import org.example.stablecoinchecker.infra.cex.TickerResponse;

public record GopaxTickerResponse(
        String close,
        String open,
        String low,
        String high,
        String volume
) {

    public TickerResponse toStableCoinTicker(final String exchangeName, final String orderCurrency) {
        return new TickerResponse(
                exchangeName,
                orderCurrency.toUpperCase(),
                close,
                open,
                low,
                high,
                volume
        );
    }
}
