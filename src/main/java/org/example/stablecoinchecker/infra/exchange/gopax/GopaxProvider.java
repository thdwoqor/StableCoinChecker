package org.example.stablecoinchecker.infra.exchange.gopax;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.exchange.CryptocurrencyExchange;
import org.example.stablecoinchecker.infra.exchange.StableCoinProvider;
import org.example.stablecoinchecker.infra.exchange.StableCoinResponse;
import org.example.stablecoinchecker.infra.exchange.Symbol;
import org.example.stablecoinchecker.infra.exchange.gopax.dto.GopaxTickerResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GopaxProvider implements StableCoinProvider {

    private final GopaxClient gopaxClient;

    @Override
    public List<StableCoinResponse> getStableCoin() {
        GopaxTickerResponse response = gopaxClient.getTicker("USDC", "KRW");
        return List.of(new StableCoinResponse(
                CryptocurrencyExchange.GOPAX,
                Symbol.USDC,
                response.price()
        ));
    }

//    @Override
//    public StableCoinResponse getTicker(final String symbol, final String currency) {
//        GopaxTickerResponse response = gopaxClient.getTicker(symbol,currency);
//        return new StableCoinResponse(response.price());
//    }
}
