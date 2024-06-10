package org.example.stablecoinchecker.infra.cex;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

public enum CryptoExchange {
    BITHUMB,
    COINONE,
    GOPAX,
    KORBIT;

    @JsonCreator
    public static CryptoExchange parsing(String inputValue) {
        return Stream.of(CryptoExchange.values())
                .filter(category -> category.toString().equals(inputValue.toUpperCase()))
                .findFirst()
                .orElse(null);
    }
}
