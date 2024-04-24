package org.example.stablecoinchecker.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stablecoinchecker.domain.StableCoin;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerResponse;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerProvider;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StableCoinService {

    private final List<StableCoinTickerProvider> stableCoinTickerProviders;

    public List<StableCoin> findStableCoin(final BigDecimal exchangeRate) {
        ArrayList<StableCoin> coins = new ArrayList<>();
        for (StableCoinTickerProvider provider : stableCoinTickerProviders) {
            for (StableCoinTickerResponse response : provider.getStableCoinTickers()) {
                coins.add(StableCoinMapper.toStableCoin(response, exchangeRate));
            }
        }
        return coins;
    }
}
