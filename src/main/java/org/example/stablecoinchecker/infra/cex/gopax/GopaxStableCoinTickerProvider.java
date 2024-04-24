package org.example.stablecoinchecker.infra.cex.gopax;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerProvider;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerResponse;
import org.example.stablecoinchecker.infra.cex.gopax.dto.GopaxTickerResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(5)
@RequiredArgsConstructor
public class GopaxStableCoinTickerProvider implements StableCoinTickerProvider {

    private final GopaxClient gopaxClient;

    @Override
    public List<StableCoinTickerResponse> getStableCoinTickers() {
        List<StableCoinTickerResponse> responses = new ArrayList<>();
        for (GopaxStableCoinSymbol value : GopaxStableCoinSymbol.values()) {
            GopaxTickerResponse response = gopaxClient.getTicker(value.getName(), "KRW");
            responses.add(response.toStableCoinTicker(value));
        }
        return responses;
    }
}
