package org.example.stablecoinchecker.infra.cex;

import java.math.BigDecimal;

public record TickerResponse(
        String cex,
        String symbol,
        BigDecimal close,
        BigDecimal open,
        BigDecimal low,
        BigDecimal high,
        BigDecimal volume
) {
}
