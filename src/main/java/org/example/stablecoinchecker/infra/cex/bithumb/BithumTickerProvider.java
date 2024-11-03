package org.example.stablecoinchecker.infra.cex.bithumb;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.StableCoinTicker;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerProvider;
import org.example.stablecoinchecker.infra.cex.bithumb.dto.BithumbTickerResponse;
import org.example.stablecoinchecker.infra.cex.coinone.dto.CoinoneTickerResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(2)
@Service
@RequiredArgsConstructor
public class BithumTickerProvider implements StableCoinTickerProvider {

    private final BithumbClient bithumbClient;

    @Override
    public List<StableCoinTicker> getStableCoin() {
        List<StableCoinTicker> responses = new ArrayList<>();
        for (BithumbStableCoin value : BithumbStableCoin.values()) {
            try {
                BithumbTickerResponse response = bithumbClient.getTicker(value.getName(), "KRW");
                responses.add(response.toStableCoinTicker(value));
            } catch (Exception e) {
            }
        }
        return responses;
    }
}
