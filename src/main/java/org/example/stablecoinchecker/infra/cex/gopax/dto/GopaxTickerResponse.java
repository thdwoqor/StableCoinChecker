package org.example.stablecoinchecker.infra.cex.gopax.dto;

import org.example.stablecoinchecker.infra.cex.CryptocurrencyExchange;
import org.example.stablecoinchecker.infra.cex.StableCoinTicker;
import org.example.stablecoinchecker.infra.cex.gopax.GopaxStableCoin;

public record GopaxTickerResponse(
        String price
) {

    public StableCoinTicker toStableCoinTicker(GopaxStableCoin symbol) {
        return new StableCoinTicker(
                CryptocurrencyExchange.GOPAX,
                symbol.getName().toUpperCase(),
                price
        );
    }
}
