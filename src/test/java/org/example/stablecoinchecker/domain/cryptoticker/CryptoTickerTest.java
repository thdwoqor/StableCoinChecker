package org.example.stablecoinchecker.domain.cryptoticker;

import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
import org.example.stablecoinchecker.domain.exchangeRate.KimchiPremiumCalculationService;
import org.example.stablecoinchecker.domain.exchangeRate.ExchangeRate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CryptoTickerTest {

    @ParameterizedTest
    @CsvSource(value = {"1400:1300:-7.1", "1300:1450:11.5"}, delimiter = ':')
    void 김프를_계산할_수_있다(String exchangeRateUsdToKrw, String price, String kimchiPremium) {
        //given
        KimchiPremiumCalculationService calculationService = new KimchiPremiumCalculationService();
        CryptoTicker cryptoTicker = new CryptoTicker(
                "UPBIT",
                "USDT",
                new Price(
                        new BigDecimal(price)
                )
        );
        ExchangeRate exchangeRate = new ExchangeRate(new BigDecimal(exchangeRateUsdToKrw));

        Assertions.assertThat(calculationService.calculate(cryptoTicker, exchangeRate)).isEqualTo(
                Double.valueOf(kimchiPremium)
        );
    }
}
