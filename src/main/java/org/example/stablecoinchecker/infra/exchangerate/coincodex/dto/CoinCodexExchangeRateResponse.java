package org.example.stablecoinchecker.infra.exchangerate.coincodex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CoinCodexExchangeRateResponse(
        @JsonProperty("fiat_rates")
        FiatRates fiatRates
) {
}
