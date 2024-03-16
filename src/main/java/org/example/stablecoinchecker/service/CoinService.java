package org.example.stablecoinchecker.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stablecoinchecker.domain.Coin;
import org.example.stablecoinchecker.infra.exchangerate.ExchangeRateClient;
import org.example.stablecoinchecker.infra.cex.EstimatedStableCoinProvider;
import org.example.stablecoinchecker.infra.cex.StableCoinProvider;
import org.example.stablecoinchecker.infra.cex.StableCoinResponse;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoinService {

    private final ExchangeRateClient exchangeRateClient;
    private final List<StableCoinProvider> stableCoinProviders;
    private final List<EstimatedStableCoinProvider> estimatedStableCoinProviders;

    public BigDecimal getExchangeRate() {
        return new BigDecimal(String.valueOf(
                exchangeRateClient.getExchangeRate().get(0).basePrice()
        ));
    }

    public List<Coin> getEstimatedStableCoin() {
        double exchangeRate = exchangeRateClient.getExchangeRate().get(0).basePrice();
        ArrayList<Coin> coins = new ArrayList<>();
        for (EstimatedStableCoinProvider provider : estimatedStableCoinProviders) {
            StableCoinResponse response = provider.getStableCoin();
            coins.add(toCoin(response, exchangeRate));
        }
        return coins;
    }

    public List<Coin> getStableCoin() {
        double exchangeRate = exchangeRateClient.getExchangeRate().get(0).basePrice();
        ArrayList<Coin> coins = new ArrayList<>();
        for (StableCoinProvider provider : stableCoinProviders) {
            for (StableCoinResponse response : provider.getStableCoin()) {
                coins.add(toCoin(response, exchangeRate));
            }
        }
        return coins;
    }

    private Coin toCoin(final StableCoinResponse response, final double exchangeRate) {
        return new Coin(
                new BigDecimal(String.valueOf(exchangeRate)),
                response.cex(),
                new BigDecimal(response.price()),
                response.symbol()
        );
    }
}
