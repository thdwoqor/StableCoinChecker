package org.example.stablecoinchecker.infra.cex.coinone;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.CryptocurrencyExchange;
import org.example.stablecoinchecker.infra.cex.StableCoinProvider;
import org.example.stablecoinchecker.infra.cex.StableCoinResponse;
import org.example.stablecoinchecker.infra.cex.Symbol;
import org.example.stablecoinchecker.infra.cex.coinone.dto.CoinoneTickerResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoinoneProvider implements StableCoinProvider {

    private final CoinoneClient coinoneClient;

    @Override
    public List<StableCoinResponse> getStableCoin() {
        CoinoneTickerResponse response = coinoneClient.getTicker("USDT", "KRW");
        String price = response.tickers().get(0).last();
        return List.of(new StableCoinResponse(
                CryptocurrencyExchange.COINONE,
                Symbol.USDT,
                price
        ));
    }

//    @Override
//    public StableCoinResponse getTicker(final String symbol, final String currency) {
//        CoinoneTickerResponse response = coinoneClient.getTicker(symbol, currency);
//        String price = response.tickers().get(0).last();
//        return new StableCoinResponse(price);
//    }


}
