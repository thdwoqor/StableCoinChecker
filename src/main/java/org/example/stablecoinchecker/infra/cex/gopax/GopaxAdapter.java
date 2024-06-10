package org.example.stablecoinchecker.infra.cex.gopax;

import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.infra.cex.CryptoExchangeClient;
import org.example.stablecoinchecker.infra.cex.TickerResponse;
import org.example.stablecoinchecker.infra.cex.gopax.dto.GopaxTickerResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(5)
@Service
@RequiredArgsConstructor
public class GopaxAdapter implements CryptoExchangeClient {

    private final GopaxClient gopaxClient;

    @Override
    public TickerResponse getTickers(final String cryptoSymbol) {
        GopaxTickerResponse response = gopaxClient.getTicker(
                cryptoSymbol,
                CryptoExchangeClient.PAYMENT_CURRENCY
        );
        return response.toStableCoinTicker(CryptoExchange.GOPAX, cryptoSymbol);
    }

    @Override
    public boolean supports(final CryptoExchange cryptoExchange) {
        return CryptoExchange.GOPAX == cryptoExchange;
    }
}
