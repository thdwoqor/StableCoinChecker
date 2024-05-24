package org.example.stablecoinchecker.infra.cex.coinone.dto;

import java.util.List;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerResponse;
import org.example.stablecoinchecker.infra.cex.coinone.CoinoneStableCoinSymbol;

public record CoinoneTickerResponse(
        String result,
        String errorCode,
        List<Ticker> tickers
) {

    public StableCoinTickerResponse toStableCoinTicker(CoinoneStableCoinSymbol symbol) {
        return new StableCoinTickerResponse(
                "COINONE",
                symbol.getName().toUpperCase(),
                tickers.get(0).last(),
                tickers.get(0).first(),
                tickers.get(0).low(),
                tickers.get(0).high(),
                tickers.get(0).targetVolume()
        );
    }
}
