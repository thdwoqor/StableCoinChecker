package org.example.stablecoinchecker.infra.cex.updit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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
}
