package org.example.stablecoinchecker.infra.coinone.dto;

import java.util.List;

public record CoinonePriceResponse(
        String result,
        String errorCode,
        List<Ticker> tickers
) {
}
