package org.example.stablecoinchecker.infra.cex.coinone.dto;

import java.util.List;
import org.example.stablecoinchecker.infra.cex.CryptocurrencyExchange;
import org.example.stablecoinchecker.infra.cex.StableCoinTicker;
import org.example.stablecoinchecker.infra.cex.coinone.CoinoneStableCoin;

public record CoinoneTickerResponse(
        String result,
        String errorCode,
        List<Ticker> tickers
) {

    public StableCoinTicker toStableCoinTicker(CoinoneStableCoin symbol) {
        return new StableCoinTicker(
                CryptocurrencyExchange.COINONE,
                symbol.getName().toUpperCase(),
                tickers.get(0).last()
        );
    }
}
