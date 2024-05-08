package org.example.stablecoinchecker.infra.cex.korbit;

import lombok.Getter;

@Getter
public enum KorbitStableCoinSymbol {
    USDT("usdt");

    private final String name;

    KorbitStableCoinSymbol(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
