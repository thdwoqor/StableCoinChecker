package org.example.stablecoinchecker.infra.cex.coinone.dto;

import java.util.List;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.infra.cex.TickerResponse;

public record CoinoneTickerResponse(
        String result,
        String errorCode,
        List<Ticker> tickers
) {

    public TickerResponse toStableCoinTicker(final CryptoExchange cryptoExchange, final String orderCurrency) {
        return new TickerResponse(
                cryptoExchange.name(),
                orderCurrency.toUpperCase(),
                tickers.get(0).last(),
                tickers.get(0).first(),
                tickers.get(0).low(),
                tickers.get(0).high(),
                tickers.get(0).targetVolume()
        );
    }
}
