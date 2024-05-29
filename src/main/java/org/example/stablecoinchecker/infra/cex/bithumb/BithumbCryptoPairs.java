package org.example.stablecoinchecker.infra.cex.bithumb;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BithumbCryptoPairs {
    USDT_KRW("USDT", "KRW");

    private final String orderCurrency;
    private final String paymentCurrency;
}
