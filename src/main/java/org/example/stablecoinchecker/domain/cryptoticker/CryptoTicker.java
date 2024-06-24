package org.example.stablecoinchecker.domain.cryptoticker;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.stablecoinchecker.domain.BaseEntity;

@Getter
@Entity
@ToString
@Table(name = "crypto_ticker")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CryptoTicker extends BaseEntity {

    private String cryptoExchange;
    private String symbol;
    @Embedded
    private Price price;
    @Column(updatable = false)
    private Long createdAt;

    public CryptoTicker(
            final String cryptoExchange,
            final String symbol,
            final Price price
    ) {
        this.cryptoExchange = cryptoExchange.toUpperCase();
        this.symbol = symbol.toUpperCase();
        this.price = price;
    }

    public BigDecimal getCurrentPrice() {
        return price.getClose();
    }

    @PrePersist
    private void setCreatedAt() {
        createdAt = Instant.now().truncatedTo(ChronoUnit.MINUTES).toEpochMilli();
    }
}
