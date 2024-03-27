package org.example.stablecoinchecker.domain;

import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
import org.example.stablecoinchecker.infra.cex.CryptocurrencyExchange;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StableCoinTest {

    @ParameterizedTest
    @CsvSource(value = {"1400:1300:-7.1","1300:1450:11.5"}, delimiter = ':')
    void 김프를_계산할_수_있다(String exchangeRate, String price, String kimchiPremium) {
        StableCoin stableCoin = new StableCoin(
                new BigDecimal(exchangeRate),
                CryptocurrencyExchange.UPBIT,
                new BigDecimal(price),
                "USDT"
        );

        Assertions.assertThat(stableCoin.calculateKimchiPremium()).isEqualTo(
                new BigDecimal(kimchiPremium)
        );
    }
}
