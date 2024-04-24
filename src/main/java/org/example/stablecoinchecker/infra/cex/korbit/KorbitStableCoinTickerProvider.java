package org.example.stablecoinchecker.infra.cex.korbit;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerProvider;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerResponse;
import org.example.stablecoinchecker.infra.cex.korbit.dto.KorbitTickerResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Order(4)
@RequiredArgsConstructor
public class KorbitStableCoinTickerProvider implements StableCoinTickerProvider {

    private final KorbitClient korbitClient;

    @Override
    public List<StableCoinTickerResponse> getStableCoinTickers() {
        List<StableCoinTickerResponse> responses = new ArrayList<>();
        for (KorbitStableCoinSymbol value : KorbitStableCoinSymbol.values()) {
            ResponseEntity<KorbitTickerResponse> response = korbitClient.getTicker(value.getName(), "krw");

            System.out.println("##");
            System.out.println(response);
            System.out.println(response.toString());
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());

            if (response.getBody() != null) {
                responses.add(response.getBody().toStableCoinTicker(value));
            }
        }
        return responses;
    }
}
