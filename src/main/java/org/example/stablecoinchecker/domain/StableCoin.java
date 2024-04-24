package org.example.stablecoinchecker.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Enumerated(value = EnumType.STRING)
    private CryptocurrencyExchange cryptocurrencyExchange;
    @Enumerated(value = EnumType.STRING)
    private Symbol symbol;
    @Embedded
    private Ticker ticker;

    public StableCoin(final BigDecimal exchangeRate, final CryptocurrencyExchange cryptocurrencyExchange,
                      final Symbol symbol,
                      final Ticker ticker) {
        this.exchangeRate = exchangeRate;
        this.cryptocurrencyExchange = cryptocurrencyExchange;
        this.symbol = symbol;
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
