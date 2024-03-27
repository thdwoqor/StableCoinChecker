package org.example.stablecoinchecker.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.CryptocurrencyExchange;

@Getter
@RequiredArgsConstructor
public class StableCoin {

    private final BigDecimal exchangeRate;
    private final CryptocurrencyExchange cryptocurrencyExchange;
    private final BigDecimal price;
    private final String symbol;

    public BigDecimal calculateKimchiPremium() {
        return price.divide(exchangeRate, 3, RoundingMode.HALF_DOWN)
                .subtract(BigDecimal.ONE)
                .multiply(new BigDecimal("100"))
                .setScale(1);
    }
}
