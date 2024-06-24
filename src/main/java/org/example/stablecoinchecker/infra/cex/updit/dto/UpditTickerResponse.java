package org.example.stablecoinchecker.infra.cex.updit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.infra.cex.TickerResponse;

public record UpditTickerResponse(
        @JsonProperty("trade_price")
        BigDecimal tradePrice,
        Long timestamp
) {
    public TickerResponse toTickerResponse(final CryptoExchange cryptoExchange, final String orderCurrency) {
        return new TickerResponse(
                cryptoExchange.name(),
                orderCurrency.toUpperCase(),
                tradePrice,
                timestamp
        );
    }
}
