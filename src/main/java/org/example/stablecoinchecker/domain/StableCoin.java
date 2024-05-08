package org.example.stablecoinchecker.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StableCoin extends BaseEntity {

    private BigDecimal exchangeRate;
    private String cryptocurrencyExchange;
    private String symbol;
    @Embedded
    private Ticker ticker;

    public StableCoin(
            final BigDecimal exchangeRate,
            final String cryptocurrencyExchange,
            final String symbol,
            final Ticker ticker
    ) {
        this.exchangeRate = exchangeRate;
        this.cryptocurrencyExchange = cryptocurrencyExchange.toUpperCase();
        this.symbol = symbol.toUpperCase();
        this.ticker = ticker;
    }

    public double calculateKimchiPremium() {
        return ticker.getCurrentPrice().divide(exchangeRate, 3, RoundingMode.HALF_DOWN)
                .subtract(BigDecimal.ONE)
                .multiply(new BigDecimal("100"))
                .setScale(1)
                .doubleValue();
    }

    public int getCurrentPrice() {
        return ticker.getCurrentPrice().intValue();
    }
}
