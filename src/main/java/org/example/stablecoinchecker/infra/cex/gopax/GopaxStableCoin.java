package org.example.stablecoinchecker.infra.cex.gopax;

import lombok.Getter;

@Getter
public enum GopaxStableCoin {
    USDT("USDT");

    private final String symbol;

    GopaxStableCoin(final String symbol) {
        this.symbol = symbol;
    }
}
