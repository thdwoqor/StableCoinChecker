package org.example.stablecoinchecker.infra.cex.coinone.dto;

import java.util.List;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.infra.cex.StableCoin;

public record CoinoneTickerResponse(
        String result,
        String errorCode,
        List<Ticker> tickers
) {

    public StableCoin toTickerResponse(final CryptoExchange cryptoExchange, final String orderCurrency) {
        return new StableCoin(
                cryptoExchange.name(),
                orderCurrency.toUpperCase(),
                tickers.get(0).last(),
                tickers.get(0).timestamp()
        );
    }
}
