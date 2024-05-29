package org.example.stablecoinchecker.controller;

import org.example.stablecoinchecker.domain.StableCoin;

public record StableCoinResponse(
        Long date,
        Double close
) {

    public static StableCoinResponse of(final StableCoin stableCoin) {
        return new StableCoinResponse(stableCoin.getCreatedAt(),
                stableCoin.getTicker().getCurrentPrice().doubleValue());
    }
}
