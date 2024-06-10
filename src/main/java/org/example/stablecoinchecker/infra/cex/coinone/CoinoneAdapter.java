package org.example.stablecoinchecker.infra.cex.coinone;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.infra.cex.CryptoExchangeClient;
import org.example.stablecoinchecker.infra.cex.TickerResponse;
import org.example.stablecoinchecker.infra.cex.coinone.dto.CoinoneTickerResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Order(3)
@RequiredArgsConstructor
public class CoinoneAdapter implements CryptoExchangeClient {

    private final CoinoneClient coinoneClient;

    @Override
    public TickerResponse getTickers(final String cryptoSymbol) {
        CoinoneTickerResponse response = coinoneClient.getTicker(
                cryptoSymbol,
                CryptoExchangeClient.PAYMENT_CURRENCY
        );
        return response.toStableCoinTicker(CryptoExchange.COINONE, cryptoSymbol);
    }

    @Override
    public boolean supports(final CryptoExchange cryptoExchange) {
        return CryptoExchange.COINONE == cryptoExchange;
    }
}
