package org.example.stablecoinchecker.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.domain.StableCoin;
import org.example.stablecoinchecker.infra.cex.StableCoinTickerResponse;
import org.example.stablecoinchecker.infra.cex.bithumb.BithumbClient;
import org.example.stablecoinchecker.infra.cex.updit.UpbitClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpbitService {

    private final UpbitClient upbitClient;
    private final BithumbClient bithumbClient;

    public StableCoin convertBtcToUsdt(final BigDecimal exchangeRate) {
        BigDecimal upbitBTC = new BigDecimal(String.valueOf(upbitClient.getTicker("BTC", "KRW").get(0).tradePrice()));
        BigDecimal bithumbBTC = new BigDecimal(bithumbClient.getTicker("BTC", "KRW").data().closingPrice());
        BigDecimal bithumbUSDT = new BigDecimal(bithumbClient.getTicker("USDT", "KRW").data().closingPrice());
        BigDecimal upbitUSDT = upbitBTC.multiply(bithumbUSDT)
                .divide(bithumbBTC, 1, RoundingMode.HALF_DOWN);

        StableCoinTickerResponse stableCoinTickerResponse = new StableCoinTickerResponse(
                "UPBIT",
                "USDT",
                upbitUSDT.toString(),
                upbitUSDT.toString(),
                upbitUSDT.toString(),
                upbitUSDT.toString()
        );

        return StableCoinMapper.toStableCoin(
                stableCoinTickerResponse,
                exchangeRate
        );
    }
}
