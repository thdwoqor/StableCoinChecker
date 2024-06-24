package org.example.stablecoinchecker.infra.cex.korbit.dto;

import java.math.BigDecimal;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.infra.cex.TickerResponse;

public record KorbitTickerResponse(
        BigDecimal last,
        Long timestamp
) {
    public TickerResponse toTickerResponse(final CryptoExchange cryptoExchange, final String orderCurrency) {
        return new TickerResponse(
                cryptoExchange.name(),
                orderCurrency.toUpperCase(),
                last,
                timestamp
        );
    }
}
