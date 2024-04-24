package org.example.stablecoinchecker.infra.cex.coinone;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerResponse;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerProvider;
import org.example.stablecoinchecker.infra.cex.coinone.dto.CoinoneTickerResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(3)
@RequiredArgsConstructor
public class CoinoneStableCoinTickerProvider implements StableCoinTickerProvider {

    private final CoinoneClient coinoneClient;

    @Override
    public List<StableCoinTickerResponse> getStableCoinTickers() {
        List<StableCoinTickerResponse> responses = new ArrayList<>();
        for (CoinoneStableCoinSymbol value : CoinoneStableCoinSymbol.values()) {
            CoinoneTickerResponse response = coinoneClient.getTicker(value.getName(), "KRW");
            responses.add(response.toStableCoinTicker(value));
        }
        return responses;
    }
}
