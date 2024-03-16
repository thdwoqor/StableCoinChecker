package org.example.stablecoinchecker.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.CryptocurrencyExchange;
import org.example.stablecoinchecker.infra.cex.Symbol;

@Getter
@RequiredArgsConstructor
public class Coin {

    private final BigDecimal exchangeRate;
    private final CryptocurrencyExchange cryptocurrencyExchange;
    private final BigDecimal price;
    private final Symbol symbol;

    public BigDecimal calculateKimchiPremium() {
        return price.divide(exchangeRate, 3, RoundingMode.HALF_DOWN)
                .subtract(BigDecimal.ONE)
                .multiply(new BigDecimal("100").setScale(1));
    }

    public Coin calculateEstimatedPrice(
            final Coin baseCoinA,
            final Coin baseCoinB
    ) {
        if (baseCoinA.cryptocurrencyExchange != baseCoinB.cryptocurrencyExchange) {
            new IllegalArgumentException();
        }
        if (baseCoinB.symbol != this.symbol) {
            new IllegalArgumentException();
        }
        return new Coin(
                this.exchangeRate,
                baseCoinA.cryptocurrencyExchange,
                baseCoinA.price.multiply(this.price)
                        .divide(baseCoinB.price, 1, RoundingMode.HALF_DOWN),
                this.symbol
        );
    }
}
