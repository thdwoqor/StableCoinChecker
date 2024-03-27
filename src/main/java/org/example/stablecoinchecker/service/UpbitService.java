package org.example.stablecoinchecker.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.CryptocurrencyExchange;
import org.example.stablecoinchecker.infra.cex.StableCoinTicker;
import org.example.stablecoinchecker.infra.cex.bithumb.BithumbClient;
import org.example.stablecoinchecker.infra.cex.updit.UpbitClient;
import org.example.stablecoinchecker.infra.telegram.dto.StableCoinInfo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpbitService {

    private final UpbitClient upbitClient;
    private final BithumbClient bithumbClient;

    public StableCoinInfo convertBtcToUsdt(final BigDecimal exchangeRate) {
        BigDecimal upbitBTC = new BigDecimal(String.valueOf(upbitClient.getTicker("BTC", "KRW").get(0).tradePrice()));
        BigDecimal bithumbBTC = new BigDecimal(bithumbClient.getTicker("BTC", "KRW").data().closingPrice());
        BigDecimal bithumbUSDT = new BigDecimal(bithumbClient.getTicker("USDT", "KRW").data().closingPrice());
        BigDecimal upbitUSDT = upbitBTC.multiply(bithumbUSDT)
                .divide(bithumbBTC, 1, RoundingMode.HALF_DOWN);

        StableCoinTicker stableCoinTicker = new StableCoinTicker(
                CryptocurrencyExchange.UPBIT,
                "USDT",
                upbitUSDT.toString());

        return StableCoinMapper.toStableCoinInfo(
                stableCoinTicker,
                exchangeRate
        );
    }
}
