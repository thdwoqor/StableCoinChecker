package org.example.stablecoinchecker.infra.cex;

import java.math.BigDecimal;

public record TickerResponse(
        String cex,
        String symbol,
        BigDecimal close,
        Long timestamp
) {
}
