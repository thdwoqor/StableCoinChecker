package org.example.stablecoinchecker.infra.cex.updit;

import lombok.Getter;

@Getter
public enum UpbitStableCoin {
    USDT("USDT");

    private final String symbol;

    UpbitStableCoin(final String symbol) {
        this.symbol = symbol;
    }
}
