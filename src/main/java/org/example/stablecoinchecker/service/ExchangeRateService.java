package org.example.stablecoinchecker.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.exchangerate.coincodex.CoinCodexExchangeRateClient;
import org.example.stablecoinchecker.infra.exchangerate.coincodex.dto.CoinCodexExchangeRateResponse;
import org.example.stablecoinchecker.infra.exchangerate.manana.MananaExchangeRateClient;
import org.example.stablecoinchecker.infra.exchangerate.manana.dto.MananaExchangeRateResponse;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private static final String USD_KRW = "KRW=X";
    private static final int FIFTEEN_MINUTES = 900;

    private final CoinCodexExchangeRateClient coinCodexExchangeRateClient;
    private final MananaExchangeRateClient mananaExchangeRateClient;

    @Retryable(
            value = {Exception.class},
            maxAttempts = 2,
            backoff = @Backoff(delay = 1000),
            recover = "recover"
    )
    public BigDecimal getExchangeRate() {
        List<MananaExchangeRateResponse> exchangeRates = mananaExchangeRateClient.getExchangeRate();

        MananaExchangeRateResponse result = exchangeRates.stream()
                .filter(response -> response.symbol().equals(USD_KRW))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("원달러 환율 정보를 불러오는 데 실패했습니다."));

        if (Instant.now().getEpochSecond() - result.timeStamp() > FIFTEEN_MINUTES) {
            throw new IllegalArgumentException("원달러 환율 정보를 불러오는 데 실패했습니다.");
        }

        return result.price();
    }

    @Recover
    public BigDecimal recover() {
        CoinCodexExchangeRateResponse exchangeRate = coinCodexExchangeRateClient.getExchangeRate();
        return exchangeRate.fiatRates().krw();
    }
}
