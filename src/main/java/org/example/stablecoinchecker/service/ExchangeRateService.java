package org.example.stablecoinchecker.service;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.domain.exchangeRate.ExchangeRate;
import org.example.stablecoinchecker.domain.exchangeRate.ExchangeRateRepository;
import org.example.stablecoinchecker.infra.exchangerate.ExchangeRateClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateClient exchangeRateClient;
    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRate save() {
        ExchangeRate exchangeRate = new ExchangeRate(
                new BigDecimal(String.valueOf(
                        exchangeRateClient.getExchangeRate().get(0).basePrice()
                )));
        return exchangeRateRepository.save(exchangeRate);
    }
}
