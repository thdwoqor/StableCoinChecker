package org.example.stablecoinchecker.infra.cex.gopax;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stablecoinchecker.infra.cex.CryptoPairs;
import org.example.stablecoinchecker.infra.cex.ExchangeClient;
import org.example.stablecoinchecker.infra.cex.TickerResponse;
import org.example.stablecoinchecker.infra.cex.gopax.dto.GopaxTickerResponse;
import org.example.stablecoinchecker.infra.cex.korbit.KorbitCryptoPairs;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(5)
@Service
@RequiredArgsConstructor
public class GopaxExchangeClient implements ExchangeClient {

    private static final String GOPAX = "GOPAX";
    private final GopaxClient gopaxClient;

    @Override
    public TickerResponse getTickers(final String orderCurrency, final String paymentCurrency) {
        GopaxTickerResponse response = gopaxClient.getTicker(orderCurrency, paymentCurrency);
        return response.toStableCoinTicker(GOPAX, orderCurrency);
    }

    @Override
    public List<CryptoPairs> getCryptoPairs() {
        return Arrays.stream(GopaxCryptoPairs.values())
                .map(pair -> new CryptoPairs(pair.getOrderCurrency(), pair.getPaymentCurrency()))
                .toList();
    }
}
