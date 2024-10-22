package org.example.stablecoinchecker.infra.cex;

import java.math.BigDecimal;


public record CryptoExchangeTickerEvent(
        String identifier,
        String symbol,
        BigDecimal price,
        Long timestamp
) {
}
