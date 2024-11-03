package org.example.stablecoinchecker.infra.cex.coinone;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.StableCoinTicker;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerProvider;
import org.example.stablecoinchecker.infra.cex.coinone.dto.CoinoneTickerResponse;
import org.example.stablecoinchecker.infra.cex.korbit.dto.KorbitTickerResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(3)
@Service
@RequiredArgsConstructor
public class CoinoneTickerProvider implements StableCoinTickerProvider {

    private final CoinoneClient coinoneClient;

    @Override
    public List<StableCoinTicker> getStableCoin() {
        List<StableCoinTicker> responses = new ArrayList<>();
        for (CoinoneStableCoin value : CoinoneStableCoin.values()) {
            try {
                CoinoneTickerResponse response = coinoneClient.getTicker(value.getName(), "KRW");
                responses.add(response.toStableCoinTicker(value));
            } catch (Exception e) {
            }
        }
        return responses;
    }
}
