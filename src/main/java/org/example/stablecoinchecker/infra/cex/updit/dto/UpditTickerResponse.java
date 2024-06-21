package org.example.stablecoinchecker.infra.cex.updit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.infra.cex.TickerResponse;

public record UpditTickerResponse(
        @JsonProperty("trade_price")
        BigDecimal tradePrice,
        @JsonProperty("opening_price")
        BigDecimal openingPrice,
        @JsonProperty("low_price")
        BigDecimal lowPrice,
        @JsonProperty("high_price")
        BigDecimal highPrice,
        @JsonProperty("acc_trade_volume")
        BigDecimal accTradeVolume
) {
    public TickerResponse toTickerResponse(final CryptoExchange cryptoExchange, final String orderCurrency) {
        return new TickerResponse(
                cryptoExchange.name(),
                orderCurrency.toUpperCase(),
                tradePrice,
                openingPrice,
                lowPrice,
                highPrice,
                accTradeVolume
        );
    }
}
