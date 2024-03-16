package org.example.stablecoinchecker.infra.exchange.updit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.exchange.CryptocurrencyExchange;
import org.example.stablecoinchecker.infra.exchange.EstimatedStableCoinProvider;
import org.example.stablecoinchecker.infra.exchange.StableCoinResponse;
import org.example.stablecoinchecker.infra.exchange.Symbol;
import org.example.stablecoinchecker.infra.exchange.bithumb.BithumbClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpbitProvider implements EstimatedStableCoinProvider {

    private final UpbitClient upbitClient;
    private final BithumbClient bithumbClient;

    @Override
    public StableCoinResponse getStableCoin() {
        BigDecimal upbitBTC = new BigDecimal(String.valueOf(upbitClient.getTicker("BTC", "KRW").get(0).tradePrice()));
        BigDecimal bithumbBTC = new BigDecimal(bithumbClient.getTicker("BTC", "KRW").data().closingPrice());
        BigDecimal bithumbUSDT = new BigDecimal(bithumbClient.getTicker("USDT", "KRW").data().closingPrice());
        BigDecimal upbitUSDT = upbitBTC.multiply(bithumbUSDT)
                .divide(bithumbBTC, 1, RoundingMode.HALF_DOWN);

        return new StableCoinResponse(
                CryptocurrencyExchange.UPBIT,
                Symbol.USDT,
                upbitUSDT.toString()
        );
    }
}
