package org.example.stablecoinchecker.infra.cex.korbit;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stablecoinchecker.infra.cex.CryptoPairs;
import org.example.stablecoinchecker.infra.cex.ExchangeClient;
import org.example.stablecoinchecker.infra.cex.TickerResponse;
import org.example.stablecoinchecker.infra.cex.korbit.dto.KorbitTickerResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(4)
@RequiredArgsConstructor
public class KorbitExchangeClient implements ExchangeClient {

    private static final String KORBIT = "KORBIT";
    private final KorbitClient korbitClient;


    @Override
    public TickerResponse getTickers(final String orderCurrency, final String paymentCurrency) {
        KorbitTickerResponse response = korbitClient.getTicker(orderCurrency, paymentCurrency);
        return response.toTickerResponse(KORBIT, orderCurrency);
    }

    @Override
    public List<CryptoPairs> getCryptoPairs() {
        return Arrays.stream(KorbitCryptoPairs.values())
                .map(pair -> new CryptoPairs(pair.getOrderCurrency(), pair.getPaymentCurrency()))
                .toList();
    }
}
