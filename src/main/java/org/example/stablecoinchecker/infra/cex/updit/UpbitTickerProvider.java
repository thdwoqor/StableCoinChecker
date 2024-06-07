package org.example.stablecoinchecker.infra.cex.updit;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.StableCoinTicker;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerProvider;
import org.example.stablecoinchecker.infra.cex.updit.dto.UpditTickerResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(1)
@Service
@RequiredArgsConstructor
public class UpbitTickerProvider implements StableCoinTickerProvider {

    private final UpbitClient upbitClient;

    @Override
    public List<StableCoinTicker> getStableCoin() {
        List<StableCoinTicker> responses = new ArrayList<>();
        for (UpbitStableCoin value : UpbitStableCoin.values()) {
            try {
                List<UpditTickerResponse> response = upbitClient.getTicker(value.getName(), "KRW");
                responses.add(response.get(0).toStableCoinTicker(value));
            } catch (Exception e) {
            }
        }
        return responses;
    }
}
