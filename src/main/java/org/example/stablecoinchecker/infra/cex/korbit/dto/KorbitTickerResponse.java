package org.example.stablecoinchecker.infra.cex.korbit.dto;

import org.example.stablecoinchecker.infra.cex.CryptocurrencyExchange;
import org.example.stablecoinchecker.infra.cex.StableCoinTicker;
import org.example.stablecoinchecker.infra.cex.korbit.KorbitStableCoin;

public record KorbitTickerResponse(
        String last,
        String open,
        String low,
        String high
) {
    public StableCoinTicker toStableCoinTicker(KorbitStableCoin symbol) {
        return new StableCoinTicker(
                CryptocurrencyExchange.KORBIT,
                symbol.getName().toUpperCase(),
                last,
                open,
                low,
                high
        );
    }
}
