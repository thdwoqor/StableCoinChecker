package org.example.stablecoinchecker.infra.cex.coinone;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerProvider;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerResponse;
import org.example.stablecoinchecker.infra.cex.coinone.dto.CoinoneTickerResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Order(3)
@RequiredArgsConstructor
public class CoinoneStableCoinTickerProvider implements StableCoinTickerProvider {

    private final CoinoneClient coinoneClient;

    @Override
    public List<StableCoinTickerResponse> getStableCoinTickers() {
        List<StableCoinTickerResponse> responses = new ArrayList<>();
        for (CoinoneStableCoinSymbol value : CoinoneStableCoinSymbol.values()) {
            try {
                CoinoneTickerResponse response = coinoneClient.getTicker(value.getName(), "KRW");
                responses.add(response.toStableCoinTicker(value));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return responses;
    }
}
