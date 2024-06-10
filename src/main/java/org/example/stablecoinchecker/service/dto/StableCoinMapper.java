package org.example.stablecoinchecker.service.dto;

import java.math.BigDecimal;
import org.example.stablecoinchecker.domain.stablecoin.StableCoin;
import org.example.stablecoinchecker.domain.stablecoin.Ticker;
import org.example.stablecoinchecker.infra.cex.TickerResponse;

public class StableCoinMapper {

    public static StableCoin toStableCoin(final TickerResponse response, final BigDecimal exchangeRate) {
        Ticker ticker = Ticker.builder()
                .currentPrice(new BigDecimal(response.close()))
                .open(new BigDecimal(response.open()))
                .low(new BigDecimal(response.low()))
                .high(new BigDecimal(response.high()))
                .volume(new BigDecimal(response.volume()))
                .build();

        return new StableCoin(
                new BigDecimal(String.valueOf(exchangeRate)),
                response.cex(),
                response.symbol(),
                ticker
        );
    }
}
