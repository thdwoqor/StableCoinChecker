package org.example.stablecoinchecker.domain.stablecoin;

import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
import org.example.stablecoinchecker.domain.stablecoin.StableCoin;
import org.example.stablecoinchecker.domain.stablecoin.Ticker;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StableCoinTest {

    @ParameterizedTest
    @CsvSource(value = {"1400:1300:-7.1", "1300:1450:11.5"}, delimiter = ':')
    void 김프를_계산할_수_있다(String exchangeRate, String price, String kimchiPremium) {
        StableCoin stableCoin = new StableCoin(
                new BigDecimal(exchangeRate),
                "UPBIT",
                "USDT",
                new Ticker(
                        new BigDecimal(price),
                        new BigDecimal(price),
                        new BigDecimal(price),
                        new BigDecimal(price),
                        BigDecimal.ONE
                )
        );

        Assertions.assertThat(stableCoin.calculateKimchiPremium()).isEqualTo(
                Double.valueOf(kimchiPremium)
        );
    }
}
