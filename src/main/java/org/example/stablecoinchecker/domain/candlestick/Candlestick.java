package org.example.stablecoinchecker.domain.candlestick;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Candlestick {

    @EmbeddedId
    private CandlestickId candlestickId;
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal high;
    private BigDecimal low;

    public Candlestick(
            final CandlestickId candlestickId,
            final BigDecimal open,
            final BigDecimal close,
            final BigDecimal high,
            final BigDecimal low
    ) {
        this.candlestickId = candlestickId;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
    }

    public static Candlestick createNew(
            final CandlestickId candlestickId,
            final BigDecimal price
    ) {
        return new Candlestick(candlestickId, price, price, price, price);
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
