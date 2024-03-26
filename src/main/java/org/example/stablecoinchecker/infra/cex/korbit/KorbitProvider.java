package org.example.stablecoinchecker.infra.cex.korbit;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.CryptocurrencyExchange;
import org.example.stablecoinchecker.infra.cex.StableCoinProvider;
import org.example.stablecoinchecker.infra.cex.StableCoinResponse;
import org.example.stablecoinchecker.infra.cex.Symbol;
import org.example.stablecoinchecker.infra.cex.korbit.dto.KorbitTickerResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KorbitProvider implements StableCoinProvider {

    private final KorbitClient korbitClient;

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
