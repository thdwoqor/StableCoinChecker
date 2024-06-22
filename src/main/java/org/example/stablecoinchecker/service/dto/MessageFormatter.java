package org.example.stablecoinchecker.service.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import org.example.stablecoinchecker.domain.cryptoticker.CryptoTicker;

@Getter
public class MessageFormatter {

    public static String formatExchangeRateMessage(final BigDecimal exchangeRate) {
        StringBuilder sb = new StringBuilder();
        sb.append("• *환율*\n");
        sb.append("```복사\n");
        sb.append(String.format("USD/KRW : %,d원\n", exchangeRate.intValue()));
        sb.append("```\n");
        return sb.toString();
    }

    public static String formatStablecoinMessage(final List<CryptoTicker> cryptoTickerData) {
        StringBuilder sb = new StringBuilder();
        sb.append("• *국내 스테이블 코인 가격*\n");
        sb.append("```복사\n");
        for (CryptoTicker coin : cryptoTickerData) {
            sb.append(formatPriceMessage(coin));
        }
        sb.append("```\n");
        return sb.toString();
    }

    private static String formatPriceMessage(final CryptoTicker cryptoTickerData) {
        return String.format(
                "%-8s(%s) : %,d원(%.1f%%)\n",
                cryptoTickerData.getCryptoExchange(),
                cryptoTickerData.getSymbol(),
                cryptoTickerData.getCurrentPrice(),
                cryptoTickerData.calculateKimchiPremium());
    }
}
