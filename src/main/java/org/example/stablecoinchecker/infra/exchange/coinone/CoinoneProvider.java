package org.example.stablecoinchecker.infra.exchange.coinone;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.exchange.CryptocurrencyExchange;
import org.example.stablecoinchecker.infra.exchange.StableCoinProvider;
import org.example.stablecoinchecker.infra.exchange.StableCoinResponse;
import org.example.stablecoinchecker.infra.exchange.Symbol;
import org.example.stablecoinchecker.infra.exchange.coinone.dto.CoinoneTickerResponse;
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
