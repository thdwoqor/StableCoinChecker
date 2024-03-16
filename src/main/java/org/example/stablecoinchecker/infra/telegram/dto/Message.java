package org.example.stablecoinchecker.infra.telegram.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import org.example.stablecoinchecker.domain.Coin;
import org.springframework.util.StringUtils;

@Getter
public class Message {

    private final String message;

    private Message(final String message) {
        this.message = message;
    }

    public static Message exchangeRateOf(final BigDecimal exchangeRate) {
        StringBuilder sb = new StringBuilder();
        sb.append("• *환율*\n");
        sb.append("```복사\n");
        sb.append(String.format("USD/KRW : %,d원\n", exchangeRate.intValue()));
        sb.append("```\n");
        return new Message(sb.toString());
    }

    public static Message estimatedPriceOf(
            final List<Coin> coins
    ) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("• *BTC 기준 USDT 환산*\n"));
        sb.append("```복사\n");
        for (Coin coin : coins) {
            sb.append(formatTicker(coin));
        }
        sb.append("```\n");
        return new Message(sb.toString());
    }

    public static Message StableCoinPricesOf(final List<Coin> coins) {
        StringBuilder sb = new StringBuilder();
        sb.append("• *국내 스테이블 코인 가격*\n");
        sb.append("```복사\n");
        for (Coin coin : coins) {
            sb.append(formatTicker(coin));
        }
        sb.append("```\n");
        return new Message(sb.toString());
    }

    private static String formatTicker(final Coin coin) {
        return String.format(
                "%-8s(%s) : %,d원(%.1f%%)\n",
                StringUtils.capitalize(coin.getCryptocurrencyExchange().toString().toLowerCase()),
                coin.getSymbol(),
                coin.getPrice().intValue(),
                coin.calculateKimchiPremium().doubleValue());
    }
}
