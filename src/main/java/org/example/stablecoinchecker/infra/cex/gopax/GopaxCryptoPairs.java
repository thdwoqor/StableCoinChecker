package org.example.stablecoinchecker.infra.cex.gopax;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GopaxCryptoPairs {
    USDT_KRW("USDT", "KRW");

    private final String orderCurrency;
    private final String paymentCurrency;
}
