package org.example.stablecoinchecker.infra.cex.gopax;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerProvider;
import org.example.stablecoinchecker.infra.cex.StableCoinTicker;
import org.example.stablecoinchecker.infra.cex.gopax.dto.GopaxTickerResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(5)
@Service
@RequiredArgsConstructor
public class GopaxTickerProvider implements StableCoinTickerProvider {

    private final GopaxClient gopaxClient;

    @Override
    public List<StableCoinTicker> getStableCoin() {
        List<StableCoinTicker> responses = new ArrayList<>();
        for (GopaxStableCoin value : GopaxStableCoin.values()) {
            GopaxTickerResponse response = gopaxClient.getTicker(value.getName(), "KRW");
            responses.add(response.toStableCoinTicker(value));
        }
        return responses;
    }
}
