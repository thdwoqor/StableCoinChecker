package org.example.stablecoinchecker.infra.cex.gopax.dto;

import java.math.BigDecimal;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.infra.cex.TickerResponse;

public record GopaxTickerResponse(
        BigDecimal close,
        BigDecimal open,
        BigDecimal low,
        BigDecimal high,
        BigDecimal volume
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
