package org.example.stablecoinchecker.infra.cex.updit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.stablecoinchecker.infra.cex.CryptocurrencyExchange;
import org.example.stablecoinchecker.infra.cex.StableCoinTicker;
import org.example.stablecoinchecker.infra.cex.updit.UpbitStableCoin;

public record UpditTickerResponse(
        @JsonProperty("trade_price")
        double tradePrice
) {

        public StableCoinTicker toStableCoinTicker(UpbitStableCoin symbol) {
                return new StableCoinTicker(
                        CryptocurrencyExchange.UPBIT,
                        symbol.getName().toUpperCase(),
                        String.valueOf(tradePrice)
                );
        }
}
