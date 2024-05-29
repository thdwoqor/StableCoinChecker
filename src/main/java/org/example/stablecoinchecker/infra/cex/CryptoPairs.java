package org.example.stablecoinchecker.infra.cex;

public record CryptoPairs(
        String orderCurrency,
        String paymentCurrency
) {
}
