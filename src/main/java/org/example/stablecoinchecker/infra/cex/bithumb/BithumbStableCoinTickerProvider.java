package org.example.stablecoinchecker.infra.cex.bithumb;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerResponse;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerProvider;
import org.example.stablecoinchecker.infra.cex.bithumb.dto.BithumbTickerResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(2)
@RequiredArgsConstructor
public class BithumbStableCoinTickerProvider implements StableCoinTickerProvider {

    private final BithumbClient bithumbClient;

    @Override
    public List<StableCoinTickerResponse> getStableCoinTickers() {
        List<StableCoinTickerResponse> responses = new ArrayList<>();
        for (BithumbStableCoinSymbol value : BithumbStableCoinSymbol.values()) {
            BithumbTickerResponse response = bithumbClient.getTicker(value.getName(), "KRW");
            responses.add(response.toStableCoinTicker(value));
        }
        return responses;
    }
}
