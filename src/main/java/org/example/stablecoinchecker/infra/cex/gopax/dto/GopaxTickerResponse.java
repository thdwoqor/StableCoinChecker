package org.example.stablecoinchecker.infra.cex.gopax.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.infra.cex.StableCoin;

public record GopaxTickerResponse(
        BigDecimal close,
        String time
) {

    public StableCoin toTickerResponse(final CryptoExchange cryptoExchange, final String orderCurrency) {
        return new StableCoin(
                cryptoExchange.name(),
                orderCurrency.toUpperCase(),
                close,
                ZonedDateTime.parse(time).toInstant().toEpochMilli()
        );
    }
}
