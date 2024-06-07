package org.example.stablecoinchecker.infra.cex.updit;

import lombok.Getter;

@Getter
public enum UpbitStableCoin {
    USDT("USDT");

    private final String name;

    UpbitStableCoin(final String name) {
        this.name = name;
    }
}
