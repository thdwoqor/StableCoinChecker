package org.example.stablecoinchecker.infra.cex.bithumb;

import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.infra.cex.CryptoExchangeClient;
import org.example.stablecoinchecker.infra.cex.TickerResponse;
import org.example.stablecoinchecker.infra.cex.bithumb.dto.BithumbTickerResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(2)
@RequiredArgsConstructor
public class BithumbAdapter implements CryptoExchangeClient {

    private final BithumbClient bithumbClient;

    @Override
    public TickerResponse getTickers(final String cryptoSymbol) {
        BithumbTickerResponse response = bithumbClient.getTicker(
                cryptoSymbol,
                CryptoExchangeClient.PAYMENT_CURRENCY
        );
        return response.toStableCoinTicker(CryptoExchange.BITHUMB, cryptoSymbol);
    }

    @Override
    public boolean supports(final CryptoExchange cryptoExchange) {
        return CryptoExchange.BITHUMB == cryptoExchange;
    }
}
