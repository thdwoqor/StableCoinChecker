package org.example.stablecoinchecker.infra.cex.korbit;

import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.infra.cex.CryptoExchangeClient;
import org.example.stablecoinchecker.infra.cex.TickerResponse;
import org.example.stablecoinchecker.infra.cex.korbit.dto.KorbitTickerResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(4)
@RequiredArgsConstructor
public class KorbitAdapter implements CryptoExchangeClient {

    private final KorbitClient korbitClient;

    @Override
    public TickerResponse getTickers(final String cryptoSymbol) {
        KorbitTickerResponse response = korbitClient.getTicker(
                cryptoSymbol.toLowerCase(),
                CryptoExchangeClient.PAYMENT_CURRENCY.toLowerCase()
        );
        return response.toTickerResponse(CryptoExchange.KORBIT, cryptoSymbol);
    }

    @Override
    public boolean supports(final CryptoExchange cryptoExchange) {
        return CryptoExchange.KORBIT == cryptoExchange;
    }
}
