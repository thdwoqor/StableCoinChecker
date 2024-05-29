package org.example.stablecoinchecker.infra.cex.gopax;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.CryptoPairs;
import org.example.stablecoinchecker.infra.cex.CexClient;
import org.example.stablecoinchecker.infra.cex.TickerResponse;
import org.example.stablecoinchecker.infra.cex.gopax.dto.GopaxTickerResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(5)
@Service
@RequiredArgsConstructor
public class GopaxCexClient implements CexClient {

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
