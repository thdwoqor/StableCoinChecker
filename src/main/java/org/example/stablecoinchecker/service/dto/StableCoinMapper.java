package org.example.stablecoinchecker.service.dto;

import java.math.BigDecimal;
import org.example.stablecoinchecker.domain.stablecoin.StableCoin;
import org.example.stablecoinchecker.domain.stablecoin.Ticker;
import org.example.stablecoinchecker.infra.cex.TickerResponse;

public class StableCoinMapper {

    public static StableCoin toStableCoin(final TickerResponse response, final BigDecimal exchangeRate) {
        Ticker ticker = Ticker.builder()
                .currentPrice(response.close())
                .open(response.open())
                .low(response.low())
                .high(response.high())
                .volume(response.volume())
                .build();

        return new StableCoin(
                exchangeRate,
                response.cex(),
                response.symbol(),
                ticker
        );
    }
}
