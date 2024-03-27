package org.example.stablecoinchecker.infra.telegram.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;

@Getter
public class Message {

    private final String message;

    private Message(final String message) {
        this.message = message;
    }

    public static Message createExchangeRateMessage(final BigDecimal exchangeRate) {
        StringBuilder sb = new StringBuilder();
        sb.append("• *환율*\n");
        sb.append("```복사\n");
        sb.append(String.format("USD/KRW : %,d원\n", exchangeRate.intValue()));
        sb.append("```\n");
        return new Message(sb.toString());
    }

    public static Message createConvertedUsdtPriceMessage(
            final StableCoinInfo stableCoinInfo
    ) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("• *BTC 기준 USDT 환산*\n"));
        sb.append("```복사\n");
        sb.append(formatStableCoinInfo(stableCoinInfo));
        sb.append("```\n");
        return new Message(sb.toString());
    }

    public static Message createStableCoinPricesMessage(final List<StableCoinInfo> stableCoinInfos) {
        StringBuilder sb = new StringBuilder();
        sb.append("• *국내 스테이블 코인 가격*\n");
        sb.append("```복사\n");
        for (StableCoinInfo coin : stableCoinInfos) {
            sb.append(formatStableCoinInfo(coin));
        }
        sb.append("```\n");
        return new Message(sb.toString());
    }

    private static String formatStableCoinInfo(final StableCoinInfo stableCoinInfo) {
        return String.format(
                "%-8s(%s) : %,d원(%.1f%%)\n",
                stableCoinInfo.cex(),
                stableCoinInfo.symbol(),
                stableCoinInfo.price(),
                stableCoinInfo.kimchiPremium());
    }
}
