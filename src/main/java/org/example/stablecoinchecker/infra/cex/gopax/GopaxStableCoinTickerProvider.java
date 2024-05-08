package org.example.stablecoinchecker.infra.cex.gopax;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerProvider;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerResponse;
import org.example.stablecoinchecker.infra.cex.gopax.dto.GopaxTickerResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Order(5)
@Service
@RequiredArgsConstructor
public class GopaxStableCoinTickerProvider implements StableCoinTickerProvider {

    private final GopaxClient gopaxClient;

    @Override
    public List<StableCoinTickerResponse> getStableCoinTickers() {
        List<StableCoinTickerResponse> responses = new ArrayList<>();
        for (GopaxStableCoinSymbol value : GopaxStableCoinSymbol.values()) {
            try {
                GopaxTickerResponse response = gopaxClient.getTicker(value.getName(), "KRW");
                responses.add(response.toStableCoinTicker(value));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return responses;
    }
}
