package org.example.stablecoinchecker.infra.cex.gopax;

import lombok.Getter;

@Getter
public enum GopaxStableCoinSymbol {
    USDC("USDC");

    private final String name;

    GopaxStableCoinSymbol(final String name) {
        this.name = name;
    }
}
