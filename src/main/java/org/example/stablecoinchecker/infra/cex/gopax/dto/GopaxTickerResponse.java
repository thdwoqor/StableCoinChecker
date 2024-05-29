package org.example.stablecoinchecker.infra.cex.gopax.dto;

import org.example.stablecoinchecker.infra.cex.TickerResponse;

public record GopaxTickerResponse(
        String close,
        String open,
        String low,
        String high,
        String volume
) {

    public TickerResponse toStableCoinTicker(final String cex, final String orderCurrency) {
        return new TickerResponse(
                cex,
                orderCurrency.toUpperCase(),
                close,
                open,
                low,
                high,
                volume
        );
    }
}
