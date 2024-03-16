package org.example.stablecoinchecker.infra.exchange.coinone.dto;

import java.util.List;

public record CoinoneTickerResponse(
        String result,
        String errorCode,
        List<Ticker> tickers
) {
}
