package org.example.stablecoinchecker.infra.cex.korbit.dto;

import java.math.BigDecimal;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.infra.cex.StableCoin;

public record KorbitTickerResponse(
        BigDecimal last,
        Long timestamp
) {
    public StableCoin toTickerResponse(final CryptoExchange cryptoExchange, final String orderCurrency) {
        return new StableCoin(
                cryptoExchange.name(),
                orderCurrency.toUpperCase(),
                last,
                timestamp
        );
    }
}
