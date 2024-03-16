package org.example.stablecoinchecker.infra.exchange.bithumb;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.exchange.CryptocurrencyExchange;
import org.example.stablecoinchecker.infra.exchange.StableCoinProvider;
import org.example.stablecoinchecker.infra.exchange.StableCoinResponse;
import org.example.stablecoinchecker.infra.exchange.Symbol;
import org.example.stablecoinchecker.infra.exchange.bithumb.dto.BithumbTickerResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BithumProvider implements StableCoinProvider {

    private final BithumbClient bithumbClient;

    @Override
    public List<StableCoinResponse> getStableCoin() {
        BithumbTickerResponse response = bithumbClient.getTicker("USDT", "KRW");
        return List.of(new StableCoinResponse(
                CryptocurrencyExchange.BITHUMB,
                Symbol.USDT,
                response.data().closingPrice()
        ));
    }

//    @Override
//    public StableCoinResponse getTicker(final String symbol, final String currency) {
//        BithumbTickerResponse response = bithumbClient.getTicker(symbol, currency);
//        return new StableCoinResponse(response.data().closingPrice());
//    }
}
