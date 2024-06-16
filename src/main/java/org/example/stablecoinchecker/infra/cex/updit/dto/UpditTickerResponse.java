package org.example.stablecoinchecker.infra.cex.updit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.infra.cex.TickerResponse;

public record UpditTickerResponse(
        @JsonProperty("trade_price")
        double tradePrice,
        @JsonProperty("opening_price")
        double openingPrice,
        @JsonProperty("low_price")
        double lowPrice,
        @JsonProperty("high_price")
        double highPrice,
        @JsonProperty("acc_trade_volume")
        double accTradeVolume
) {
    public TickerResponse toTickerResponse(final CryptoExchange cryptoExchange, final String orderCurrency) {
        return new TickerResponse(
                cryptoExchange.name(),
                orderCurrency.toUpperCase(),
                String.valueOf(tradePrice),
                String.valueOf(openingPrice),
                String.valueOf(lowPrice),
                String.valueOf(highPrice),
                String.valueOf(accTradeVolume)
        );
    }
}
