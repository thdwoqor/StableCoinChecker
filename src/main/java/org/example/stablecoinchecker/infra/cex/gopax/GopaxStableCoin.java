package org.example.stablecoinchecker.infra.cex.gopax;

import lombok.Getter;

@Getter
public enum GopaxStableCoin {
    USDC("USDC");

    private final String name;

    GopaxStableCoin(final String name) {
        this.name = name;
    }
}
