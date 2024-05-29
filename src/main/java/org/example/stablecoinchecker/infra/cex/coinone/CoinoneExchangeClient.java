package org.example.stablecoinchecker.infra.cex.coinone;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stablecoinchecker.infra.cex.CryptoPairs;
import org.example.stablecoinchecker.infra.cex.ExchangeClient;
import org.example.stablecoinchecker.infra.cex.TickerResponse;
import org.example.stablecoinchecker.infra.cex.coinone.dto.CoinoneTickerResponse;
import org.example.stablecoinchecker.infra.cex.gopax.GopaxCryptoPairs;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Order(3)
@RequiredArgsConstructor
public class CoinoneExchangeClient implements ExchangeClient {

    private static final String COINONE = "COINONE";
    private final CoinoneClient coinoneClient;

    @Override
    public TickerResponse getTickers(final String orderCurrency, final String paymentCurrency) {
        CoinoneTickerResponse response = coinoneClient.getTicker(orderCurrency, paymentCurrency);
        return response.toStableCoinTicker(COINONE, orderCurrency);
    }

    @Override
    public List<CryptoPairs> getCryptoPairs() {
        return Arrays.stream(CoinoneCryptoPairs.values())
                .map(pair -> new CryptoPairs(pair.getOrderCurrency(), pair.getPaymentCurrency()))
                .toList();
    }
}
