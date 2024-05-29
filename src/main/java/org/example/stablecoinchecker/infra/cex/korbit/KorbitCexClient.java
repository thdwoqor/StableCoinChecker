package org.example.stablecoinchecker.infra.cex.korbit;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.CryptoPairs;
import org.example.stablecoinchecker.infra.cex.CexClient;
import org.example.stablecoinchecker.infra.cex.TickerResponse;
import org.example.stablecoinchecker.infra.cex.korbit.dto.KorbitTickerResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(4)
@RequiredArgsConstructor
public class KorbitCexClient implements CexClient {

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
