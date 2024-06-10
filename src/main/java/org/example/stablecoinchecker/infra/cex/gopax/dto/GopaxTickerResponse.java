package org.example.stablecoinchecker.infra.cex.gopax.dto;

import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.infra.cex.TickerResponse;

public record GopaxTickerResponse(
        String close,
        String open,
        String low,
        String high,
        String volume
) {

    public TickerResponse toStableCoinTicker(final CryptoExchange cryptoExchange, final String orderCurrency) {
        return new TickerResponse(
                cryptoExchange.name(),
                orderCurrency.toUpperCase(),
                close,
                open,
                low,
                high,
                volume
        );
    }
}
