package org.example.stablecoinchecker.service;

import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.domain.exchangeRate.ExchangeRate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRateUpdateService {

    private final ExchangeRateRequester exchangeRateRequester;
    private final ExchangeRateService exchangeRateService;

    public ExchangeRate updateExchangeRate() {
        ExchangeRate exchangeRate = exchangeRateRequester.getCurrentExchangeRate();
        return exchangeRateService.save(exchangeRate);
    }
}
