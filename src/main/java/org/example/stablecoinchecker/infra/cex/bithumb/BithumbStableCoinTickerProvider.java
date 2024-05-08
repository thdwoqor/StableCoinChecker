package org.example.stablecoinchecker.infra.cex.bithumb;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerResponse;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerProvider;
import org.example.stablecoinchecker.infra.cex.bithumb.dto.BithumbTickerResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Order(2)
@RequiredArgsConstructor
public class BithumbStableCoinTickerProvider implements StableCoinTickerProvider {

    private final BithumbClient bithumbClient;

    @Override
    public List<StableCoinTickerResponse> getStableCoinTickers() {
        List<StableCoinTickerResponse> responses = new ArrayList<>();
        for (BithumbStableCoinSymbol value : BithumbStableCoinSymbol.values()) {
            try{
                BithumbTickerResponse response = bithumbClient.getTicker(value.getName(), "KRW");
                responses.add(response.toStableCoinTicker(value));
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
        return responses;
    }
}
