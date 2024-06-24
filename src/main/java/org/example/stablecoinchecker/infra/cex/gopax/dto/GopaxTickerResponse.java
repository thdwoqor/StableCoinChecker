package org.example.stablecoinchecker.infra.cex.gopax.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.infra.cex.TickerResponse;

public record GopaxTickerResponse(
        BigDecimal close,
        String time
) {

    public TickerResponse toStableCoinTicker(final CryptoExchange cryptoExchange, final String orderCurrency) {
        return new TickerResponse(
                cryptoExchange.name(),
                orderCurrency.toUpperCase(),
                close,
                ZonedDateTime.parse(time).toInstant().toEpochMilli()
        );
    }
}
