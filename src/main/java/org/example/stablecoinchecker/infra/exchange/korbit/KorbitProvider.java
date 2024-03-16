package org.example.stablecoinchecker.infra.exchange.korbit;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.exchange.CryptocurrencyExchange;
import org.example.stablecoinchecker.infra.exchange.StableCoinProvider;
import org.example.stablecoinchecker.infra.exchange.StableCoinResponse;
import org.example.stablecoinchecker.infra.exchange.Symbol;
import org.example.stablecoinchecker.infra.exchange.korbit.dto.KorbitTickerResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KorbitProvider implements StableCoinProvider {

    private final KorbitClient korbitClient;

//    @Override
//    public StableCoinResponse getTicker(final String symbol, final String currency) {
//        KorbitTickerResponse response = korbitClient.getTicker(symbol.toLowerCase(), currency.toLowerCase());
//        return new StableCoinResponse(response.last());
//    }

    @Override
    public List<StableCoinResponse> getStableCoin() {
        KorbitTickerResponse response = korbitClient.getTicker("usdc", "krw");
        return List.of(new StableCoinResponse(
                CryptocurrencyExchange.KORBIT,
                Symbol.USDC,
                response.last()
        ));
    }
}
