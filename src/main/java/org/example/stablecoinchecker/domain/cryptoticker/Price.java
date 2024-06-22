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

    public Price(final BigDecimal close) {
        this.close = close;
    }
}
