package org.example.stablecoinchecker.infra.cex;

import java.util.List;

public interface CexClient {

    TickerResponse getTickers(String orderCurrency, String paymentCurrency);

    List<CryptoPairs> getCryptoPairs();
}
