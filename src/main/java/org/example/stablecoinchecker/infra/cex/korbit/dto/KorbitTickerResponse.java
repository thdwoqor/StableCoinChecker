package org.example.stablecoinchecker.infra.cex.korbit.dto;

import org.example.stablecoinchecker.infra.cex.StableCoinTickerResponse;
import org.example.stablecoinchecker.infra.cex.korbit.KorbitStableCoinSymbol;

public record KorbitTickerResponse(
        String last,
        String open,
        String low,
        String high
) {
    public StableCoinTickerResponse toStableCoinTicker(KorbitStableCoinSymbol symbol) {
        return new StableCoinTickerResponse(
                "KORBIT",
                symbol.getName().toUpperCase(),
                last,
                open,
                low,
                high
        );
    }
}
