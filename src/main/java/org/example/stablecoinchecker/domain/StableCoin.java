package org.example.stablecoinchecker.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "new_stable_coin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StableCoin extends BaseEntity {

    private BigDecimal exchangeRate;
    private String cex;
    private String symbol;
    @Embedded
    private Ticker ticker;
    @Column(updatable = false)
    private Long createdAt;

    public StableCoin(
            final BigDecimal exchangeRate,
            final String cex,
            final String symbol,
            final Ticker ticker
    ) {
        this.exchangeRate = exchangeRate;
        this.cex = cex.toUpperCase();
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

    @PrePersist
    private void setCreatedAt(){
        createdAt = Instant.now().truncatedTo(ChronoUnit.MINUTES).toEpochMilli();
    }
}
