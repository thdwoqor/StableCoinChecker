package org.example.stablecoinchecker.infra.cex.updit;

import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.infra.cex.CryptoExchangeClient;
import org.example.stablecoinchecker.infra.cex.TickerResponse;
import org.example.stablecoinchecker.infra.cex.updit.dto.UpditTickerResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(1)
@RequiredArgsConstructor
public class UpbitAdapter implements CryptoExchangeClient {

    private final UpbitClient upbitClient;

    @Override
    public TickerResponse getTickers(final String cryptoSymbol) {
        UpditTickerResponse response = upbitClient.getTicker(
                cryptoSymbol,
                CryptoExchangeClient.PAYMENT_CURRENCY
        ).get(0);
        return response.toTickerResponse(CryptoExchange.UPBIT, cryptoSymbol);
    }

    @Override
    public boolean supports(final CryptoExchange cryptoExchange) {
        return CryptoExchange.UPBIT == cryptoExchange;
    }
}
