package org.example.stablecoinchecker.domain.candlestick;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Candlestick extends BaseEntity {

    @Embedded
    private Code code;
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal high;
    private BigDecimal low;
    @Enumerated(value = EnumType.STRING)
    private TimeInterval timeInterval;
    private Long timestamp;

    public Candlestick(final Code code, final BigDecimal open,
                       final BigDecimal close, final BigDecimal high,
                       final BigDecimal low, final TimeInterval timeInterval, final Long timestamp) {
        this.code = code;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.timeInterval = timeInterval;
        this.timestamp = timestamp;
    }

    public static Candlestick oneMinute(final Code code,
                                        final BigDecimal price, final Long timestamp) {
        long epochMilli = Instant.ofEpochMilli(timestamp - (timestamp % 60 * 1000) + 60 * 2000)
                .truncatedTo(ChronoUnit.MINUTES).toEpochMilli();
        return new Candlestick(code, price, price, price, price, TimeInterval.MIN1, epochMilli);
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
