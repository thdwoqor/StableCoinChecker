package org.example.stablecoinchecker.infra.cex.gopax.dto;

import org.example.stablecoinchecker.infra.cex.StableCoinTickerResponse;
import org.example.stablecoinchecker.infra.cex.gopax.GopaxStableCoinSymbol;

public record GopaxTickerResponse(
        String close,
        String open,
        String low,
        String high
) {

    public StableCoinTickerResponse toStableCoinTicker(GopaxStableCoinSymbol symbol) {
        return new StableCoinTickerResponse(
                "GOPAX",
                symbol.getName().toUpperCase(),
                close,
                open,
                low,
                high
        );
    }
}
