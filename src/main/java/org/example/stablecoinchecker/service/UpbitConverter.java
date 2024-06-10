package org.example.stablecoinchecker.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.domain.stablecoin.StableCoin;
import org.example.stablecoinchecker.infra.cex.TickerResponse;
import org.example.stablecoinchecker.infra.cex.bithumb.BithumbClient;
import org.example.stablecoinchecker.infra.cex.updit.UpbitClient;
import org.example.stablecoinchecker.service.dto.StableCoinMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpbitConverter {

    private final UpbitClient upbitClient;
    private final BithumbClient bithumbClient;

    public StableCoin convertBtcToUsdt(final BigDecimal exchangeRate) {
        BigDecimal upbitBTC = new BigDecimal(String.valueOf(upbitClient.getTicker("BTC", "KRW").get(0).tradePrice()));
        BigDecimal bithumbBTC = new BigDecimal(bithumbClient.getTicker("BTC", "KRW").data().closingPrice());
        BigDecimal bithumbUSDT = new BigDecimal(bithumbClient.getTicker("USDT", "KRW").data().closingPrice());
        BigDecimal upbitUSDT = upbitBTC.multiply(bithumbUSDT)
                .divide(bithumbBTC, 1, RoundingMode.HALF_DOWN);

        TickerResponse tickerResponse = new TickerResponse(
                "UPBIT",
                "USDT",
                upbitUSDT.toString(),
                upbitUSDT.toString(),
                upbitUSDT.toString(),
                upbitUSDT.toString(),
                BigDecimal.ONE.toString()
        );

        return StableCoinMapper.toStableCoin(
                tickerResponse,
                exchangeRate
        );
    }
}
