package org.example.stablecoinchecker.domain.candlestick;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Code {

    @Enumerated(value = EnumType.STRING)
    private CryptoExchange cryptoExchange;
    @Enumerated(value = EnumType.STRING)
    private Symbol symbol;

    public Code(final CryptoExchange cryptoExchange, final Symbol symbol) {
        this.cryptoExchange = cryptoExchange;
        this.symbol = symbol;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Code code)) {
            return false;
        }
        return cryptoExchange == code.cryptoExchange && symbol == code.symbol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cryptoExchange, symbol);
    }
}
