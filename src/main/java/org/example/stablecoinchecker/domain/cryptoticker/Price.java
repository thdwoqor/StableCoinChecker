package org.example.stablecoinchecker.domain.cryptoticker;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Price {

    private BigDecimal close;
    private BigDecimal open;
    private BigDecimal low;
    private BigDecimal high;

    public Price(
            final BigDecimal close,
            final BigDecimal open,
            final BigDecimal low,
            final BigDecimal high
    ) {
        this.close = close;
        this.open = open;
        this.low = low;
        this.high = high;
    }

    public Price(final BigDecimal close) {
        this.close = close;
    }
}
