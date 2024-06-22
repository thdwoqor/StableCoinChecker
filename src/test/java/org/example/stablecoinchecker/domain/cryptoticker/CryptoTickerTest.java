package org.example.stablecoinchecker.domain.cryptoticker;

import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
import org.example.stablecoinchecker.domain.cryptoticker.CryptoTicker;
import org.example.stablecoinchecker.domain.cryptoticker.Price;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CryptoTickerTest {

    @ParameterizedTest
    @CsvSource(value = {"1400:1300:-7.1", "1300:1450:11.5"}, delimiter = ':')
    void 김프를_계산할_수_있다(String exchangeRate, String price, String kimchiPremium) {
        CryptoTicker cryptoTicker = new CryptoTicker(
                new BigDecimal(exchangeRate),
                "UPBIT",
                "USDT",
                new Price(
                        new BigDecimal(price)
                )
        );

        Assertions.assertThat(cryptoTicker.calculateKimchiPremium()).isEqualTo(
                Double.valueOf(kimchiPremium)
        );
    }
}
