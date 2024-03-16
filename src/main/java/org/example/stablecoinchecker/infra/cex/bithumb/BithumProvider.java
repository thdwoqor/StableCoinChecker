package org.example.stablecoinchecker.infra.cex.bithumb;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.CryptocurrencyExchange;
import org.example.stablecoinchecker.infra.cex.StableCoinProvider;
import org.example.stablecoinchecker.infra.cex.StableCoinResponse;
import org.example.stablecoinchecker.infra.cex.Symbol;
import org.example.stablecoinchecker.infra.cex.bithumb.dto.BithumbTickerResponse;
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
