package org.example.stablecoinchecker.service;

import java.math.BigDecimal;
import org.example.stablecoinchecker.domain.CryptocurrencyExchange;
import org.example.stablecoinchecker.domain.StableCoin;
import org.example.stablecoinchecker.domain.Symbol;
import org.example.stablecoinchecker.domain.Ticker;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerResponse;

public class StableCoinMapper {

    public static StableCoin toStableCoin(final StableCoinTickerResponse response, final BigDecimal exchangeRate) {
        Ticker ticker = Ticker.builder()
                .currentPrice(new BigDecimal(response.close()))
                .open(new BigDecimal(response.open()))
                .low(new BigDecimal(response.low()))
                .high(new BigDecimal(response.high()))
                .build();

        return new StableCoin(
                new BigDecimal(String.valueOf(exchangeRate)),
                CryptocurrencyExchange.valueOf(response.cex().toUpperCase()),
                Symbol.valueOf(response.symbol().toUpperCase()),
                ticker
        );
    }
}
