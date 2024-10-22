package org.example.stablecoinchecker.domain.candlestick;

import java.util.NoSuchElementException;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum Symbol {
    USDT,
    USDC,
    BTC;

    public static Symbol from(final String name) {
        return Stream.of(Symbol.values())
                .filter(category -> category.toString().equals(name.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("해당 이름의 심볼을 찾을 수 없습니다: " + name));
    }

}
