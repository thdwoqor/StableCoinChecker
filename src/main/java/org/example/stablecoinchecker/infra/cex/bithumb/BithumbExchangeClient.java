package org.example.stablecoinchecker.infra.cex.bithumb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stablecoinchecker.infra.cex.CryptoPairs;
import org.example.stablecoinchecker.infra.cex.TickerResponse;
import org.example.stablecoinchecker.infra.cex.ExchangeClient;
import org.example.stablecoinchecker.infra.cex.bithumb.dto.BithumbTickerResponse;
import org.example.stablecoinchecker.infra.cex.gopax.GopaxCryptoPairs;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(2)
@RequiredArgsConstructor
public class BithumbExchangeClient implements ExchangeClient {

    private static final String BITHUMB = "BITHUMB";
    private final BithumbClient bithumbClient;

    @Override
    public TickerResponse getTickers(final String orderCurrency, final String paymentCurrency) {
        BithumbTickerResponse response = bithumbClient.getTicker(orderCurrency, paymentCurrency);
        return response.toStableCoinTicker(BITHUMB,orderCurrency);
    }

    @Override
    public List<CryptoPairs> getCryptoPairs() {
        return Arrays.stream(BithumbCryptoPairs.values())
                .map(pair -> new CryptoPairs(pair.getOrderCurrency(), pair.getPaymentCurrency()))
                .toList();
    }
}
