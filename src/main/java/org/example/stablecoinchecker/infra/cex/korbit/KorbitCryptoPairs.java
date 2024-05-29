package org.example.stablecoinchecker.infra.cex.korbit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KorbitCryptoPairs {
    USDT_KRW("usdt", "krw");

    private final String orderCurrency;
    private final String paymentCurrency;
}
