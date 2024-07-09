package org.example.stablecoinchecker.infra.exchangerate.dto;

import java.math.BigDecimal;

public record ExchangeRateResponse(
        FiatRates fiat_rates
) {
}
