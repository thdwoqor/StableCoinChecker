package org.example.stablecoinchecker.domain.candlestick;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.stablecoinchecker.domain.BaseEntity;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Candlestick {

    @Embedded
    private Code code;
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal high;
    private BigDecimal low;
    @Embedded
    private Timestamp timestamp;

    public Candlestick(
            final Code code,
            final BigDecimal open,
            final BigDecimal close,
            final BigDecimal high,
            final BigDecimal low,
            final Timestamp timestamp
    ) {
        this.code = code;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.timestamp = timestamp;
    }

    public static Candlestick createNew(
            final Code code,
            final BigDecimal price,
            final Timestamp timestamp
    ) {
        return new Candlestick(code, price, price, price, price, timestamp);
    }

    public void update(final BigDecimal price) {
        this.close = price;
        if (price.compareTo(low) < 0) {
            this.low = price;
        }
        if (price.compareTo(high) > 0) {
            this.high = price;
        }
    }
}
