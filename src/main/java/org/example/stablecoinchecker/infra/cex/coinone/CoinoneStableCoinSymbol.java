package org.example.stablecoinchecker.infra.cex.coinone;

import lombok.Getter;

@Getter
public enum CoinoneStableCoinSymbol {
    USDT("USDT"),
    USDC("USDC")
    ;

    private final String name;

    CoinoneStableCoinSymbol(final String name) {
        this.name = name;
    }
}
