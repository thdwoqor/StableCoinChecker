package org.example.stablecoinchecker.domain.candlestick;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum TimeInterval {

    MIN1(60),
    MIN5(60 * 5),
    MIN15(60 * 15),
    MIN30(60 * 30),
    HOUR1(60 * 60),
    HOUR4(60 * 60 * 4),
    DAY1(60 * 60 * 24),
    WEEK1(60 * 60 * 24 * 7),
    ;

    private int second;

    TimeInterval(final int second) {
        this.second = second;
    }

    public static TimeInterval from(final int second) {
        return Arrays.stream(values())
                .filter(time -> time.second == second)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 Time Interval을 찾을 수 없습니다."));
    }

}
