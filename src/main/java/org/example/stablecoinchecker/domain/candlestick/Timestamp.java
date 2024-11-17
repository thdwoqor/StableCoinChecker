package org.example.stablecoinchecker.domain.candlestick;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Timestamp {

    @Enumerated(value = EnumType.STRING)
    private TimeInterval timeInterval;
    private Long timestamp;

    public Timestamp(final TimeInterval timeInterval, final Long timestamp) {
        this.timeInterval = timeInterval;
        this.timestamp = ceilToInterval(timeInterval, timestamp);
    }

    private long ceilToInterval(final TimeInterval timeInterval, final Long timestamp) {
        int second = timeInterval.getSecond();
        return Instant.ofEpochMilli(timestamp - (timestamp % (second * 1000L)) + (second * 1000L))
                .truncatedTo(ChronoUnit.MINUTES).toEpochMilli();
    }
}
