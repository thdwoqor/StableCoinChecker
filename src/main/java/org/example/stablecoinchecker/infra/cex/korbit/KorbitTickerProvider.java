package org.example.stablecoinchecker.infra.cex.korbit;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.StableCoinTicker;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerProvider;
import org.example.stablecoinchecker.infra.cex.korbit.dto.KorbitTickerResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KorbitTickerProvider implements StableCoinTickerProvider {

    private final KorbitClient korbitClient;

    @Override
    public List<StableCoinTicker> getStableCoin() {
        List<StableCoinTicker> responses = new ArrayList<>();
        for (KorbitStableCoin value : KorbitStableCoin.values()) {
            try {
                KorbitTickerResponse response = korbitClient.getTicker(value.getName(), "krw");
                responses.add(response.toStableCoinTicker(value));
            } catch (Exception e) {
            }
        }
        return responses;
    }
}
