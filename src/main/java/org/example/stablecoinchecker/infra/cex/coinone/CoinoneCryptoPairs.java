package org.example.stablecoinchecker.infra.cex.coinone;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoinoneCryptoPairs {
    USDT_KRW("USDT", "KRW"),
    USDC_KRW("USDC", "KRW");

    private final String orderCurrency;
    private final String paymentCurrency;
}
