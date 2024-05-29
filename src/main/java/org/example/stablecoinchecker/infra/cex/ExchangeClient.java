package org.example.stablecoinchecker.infra.cex;

import java.util.List;

public interface ExchangeClient {

    TickerResponse getTickers(String orderCurrency, String paymentCurrency);

    List<CryptoPairs> getCryptoPairs();
}
