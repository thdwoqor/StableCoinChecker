package org.example.stablecoinchecker.service.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import org.example.stablecoinchecker.domain.StableCoin;

@Getter
public class MessageFormatter {

    public static String createExchangeRateMessage(final BigDecimal exchangeRate) {
        StringBuilder sb = new StringBuilder();
        sb.append("• *환율*\n");
        sb.append("```복사\n");
        sb.append(String.format("USD/KRW : %,d원\n", exchangeRate.intValue()));
        sb.append("```\n");
        return sb.toString();
    }

    public static String createConvertedUsdtPriceMessage(
            final StableCoin stableCoinData
    ) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("• *BTC 기준 USDT 환산*\n"));
        sb.append("```복사\n");
        sb.append(formatStableCoinInfo(stableCoinData));
        sb.append("```\n");
        return sb.toString();
    }

    public static String createStableCoinPricesMessage(final List<StableCoin> stableCoinData) {
        StringBuilder sb = new StringBuilder();
        sb.append("• *국내 스테이블 코인 가격*\n");
        sb.append("```복사\n");
        for (StableCoin coin : stableCoinData) {
            sb.append(formatStableCoinInfo(coin));
        }
        sb.append("```\n");
        return sb.toString();
    }

    private static String formatStableCoinInfo(final StableCoin stableCoinData) {
        return String.format(
                "%-8s(%s) : %,d원(%.1f%%)\n",
                stableCoinData.getCryptocurrencyExchange().name(),
                stableCoinData.getSymbol().name(),
                stableCoinData.getCurrentPrice(),
                stableCoinData.calculateKimchiPremium());
    }
}
