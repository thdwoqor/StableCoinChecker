package org.example.stablecoinchecker.infra.cex.korbit;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerProvider;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerResponse;
import org.example.stablecoinchecker.infra.cex.korbit.dto.KorbitTickerResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Order(4)
@RequiredArgsConstructor
public class KorbitStableCoinTickerProvider implements StableCoinTickerProvider {

    private final KorbitClient korbitClient;

    @Override
    public List<StableCoinTickerResponse> getStableCoinTickers() {
        List<StableCoinTickerResponse> responses = new ArrayList<>();
        for (KorbitStableCoinSymbol value : KorbitStableCoinSymbol.values()) {
            try {
                KorbitTickerResponse response = korbitClient.getTicker(value.getName(), "krw");
                responses.add(response.toStableCoinTicker(value));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return responses;
    }
}
