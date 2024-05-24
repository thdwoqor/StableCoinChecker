package org.example.stablecoinchecker.domain;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticker {

    private BigDecimal currentPrice;
    private BigDecimal open;
    private BigDecimal low;
    private BigDecimal high;
    private BigDecimal volume;

    @Builder
    public Ticker(final BigDecimal currentPrice, final BigDecimal open, final BigDecimal low, final BigDecimal high,
                  final BigDecimal volume) {
        this.currentPrice = currentPrice;
        this.open = open;
        this.low = low;
        this.high = high;
        this.volume = volume;
    }
}
