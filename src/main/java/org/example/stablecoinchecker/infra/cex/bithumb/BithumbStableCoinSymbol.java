package org.example.stablecoinchecker.infra.cex.bithumb;

import lombok.Getter;

@Getter
public enum BithumbStableCoinSymbol {
    USDT("USDT");

    private final String name;

    BithumbStableCoinSymbol(final String name) {
        this.name = name;
    }
}
