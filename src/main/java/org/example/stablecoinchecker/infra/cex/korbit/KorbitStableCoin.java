package org.example.stablecoinchecker.infra.cex.korbit;

import lombok.Getter;

@Getter
public enum KorbitStableCoin {
    USDT("usdt"),
    USDC("usdc");

    private final String name;

    KorbitStableCoin(final String name) {
        this.name = name;
    }
}
