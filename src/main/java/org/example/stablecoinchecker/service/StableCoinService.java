package org.example.stablecoinchecker.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stablecoinchecker.infra.cex.StableCoinTicker;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerProvider;
import org.example.stablecoinchecker.infra.telegram.dto.StableCoinInfo;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StableCoinService {

    private final List<StableCoinTickerProvider> stableCoinTickerProviders;

    public List<StableCoinInfo> findStableCoin(final BigDecimal exchangeRate) {
        ArrayList<StableCoinInfo> coins = new ArrayList<>();
        for (StableCoinTickerProvider provider : stableCoinTickerProviders) {
            for (StableCoinTicker response : provider.getStableCoin()) {
                coins.add(StableCoinMapper.toStableCoinInfo(response, exchangeRate));
            }
        }
        return coins;
    }
}
