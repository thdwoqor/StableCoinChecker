package org.example.stablecoinchecker.infra.cex.coinone;

import lombok.Getter;

@Getter
public enum CoinoneStableCoin {
    USDT("USDT"),
    USDC("USDC")
    ;

    private final String name;

    CoinoneStableCoin(final String name) {
        this.name = name;
    }
}
