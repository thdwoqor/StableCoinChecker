package org.example.stablecoinchecker.infra.cex.coinone;

import lombok.Getter;

@Getter
public enum CoinoneStableCoin {
    USDT("USDT"),
    USDC("USDC");

    private final String symbol;

    CoinoneStableCoin(final String symbol) {
        this.symbol = symbol;
    }
}
