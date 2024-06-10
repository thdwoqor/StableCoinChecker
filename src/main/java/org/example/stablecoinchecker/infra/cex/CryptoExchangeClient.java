package org.example.stablecoinchecker.infra.cex;

public interface CryptoExchangeClient {
    String PAYMENT_CURRENCY = "KRW";

    TickerResponse getTickers(String cryptoSymbol);

    boolean supports(CryptoExchange cryptoExchange);
}
