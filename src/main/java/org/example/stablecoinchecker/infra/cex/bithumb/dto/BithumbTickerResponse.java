package org.example.stablecoinchecker.infra.cex.bithumb.dto;

import org.example.stablecoinchecker.infra.cex.StableCoinTickerResponse;
import org.example.stablecoinchecker.infra.cex.bithumb.BithumbStableCoinSymbol;

public record BithumbTickerResponse(
        String status,
        Data data
) {

    public StableCoinTickerResponse toStableCoinTicker(BithumbStableCoinSymbol symbol) {
        return new StableCoinTickerResponse(
                "BITHUMB",
                symbol.getName().toUpperCase(),
                data.closingPrice(),
                data.openingPrice(),
                data.minPrice(),
                data.maxPrice(),
                data.unitsTraded()
        );
    }
}
