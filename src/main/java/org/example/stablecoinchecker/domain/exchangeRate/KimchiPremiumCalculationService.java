package org.example.stablecoinchecker.domain.exchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.example.stablecoinchecker.domain.cryptoticker.CryptoTicker;
import org.springframework.stereotype.Service;

@Service
public class KimchiPremiumCalculationService {

    public double calculate(final CryptoTicker cryptoTicker, final ExchangeRate exchangeRate) {
        return cryptoTicker.getCurrentPrice().divide(exchangeRate.getPrice(), 3, RoundingMode.HALF_DOWN)
                .subtract(BigDecimal.ONE)
                .multiply(new BigDecimal("100"))
                .setScale(1)
                .doubleValue();
    }
}
