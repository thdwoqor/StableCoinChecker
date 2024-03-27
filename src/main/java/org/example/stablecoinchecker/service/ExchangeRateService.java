package org.example.stablecoinchecker.service;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.exchangerate.ExchangeRateClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateClient exchangeRateClient;

    public BigDecimal getExchangeRate() {
        return new BigDecimal(String.valueOf(
                exchangeRateClient.getExchangeRate().get(0).basePrice()
        ));
    }
}
