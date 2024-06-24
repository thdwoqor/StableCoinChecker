package org.example.stablecoinchecker.service.dto;

import java.util.List;
import lombok.Getter;
import org.example.stablecoinchecker.domain.exchangeRate.KimchiPremiumCalculationService;
import org.example.stablecoinchecker.domain.cryptoticker.CryptoTicker;
import org.example.stablecoinchecker.domain.exchangeRate.ExchangeRate;

@Getter
public class MessageFormatter {

    public static String formatExchangeRateMessage(final ExchangeRate exchangeRate) {
        StringBuilder sb = new StringBuilder();
        sb.append("• *환율*\n");
        sb.append("```복사\n");
        sb.append(String.format("USD/KRW : %,d원\n", exchangeRate.getValue().intValue()));
        sb.append("```\n");
        return sb.toString();
    }

    public static String formatStablecoinMessage(
            final List<CryptoTicker> cryptoTicker,
            final ExchangeRate exchangeRate,
            final KimchiPremiumCalculationService calculationService
    ) {
        StringBuilder sb = new StringBuilder();
        sb.append("• *국내 스테이블 코인 가격*\n");
        sb.append("```복사\n");
        for (CryptoTicker ticker : cryptoTicker) {
            sb.append(formatPriceMessage(ticker, exchangeRate, calculationService));
        }
        sb.append("```\n");
        return sb.toString();
    }

    private static String formatPriceMessage(
            final CryptoTicker cryptoTicker,
            final ExchangeRate exchangeRate,
            final KimchiPremiumCalculationService kimchiPremiumCalculationService
    ) {
        return String.format(
                "%-8s(%s) : %,d원(%.1f%%)\n",
                cryptoTicker.getCryptoExchange(),
                cryptoTicker.getSymbol(),
                cryptoTicker.getCurrentPrice().intValue(),
                kimchiPremiumCalculationService.calculate(cryptoTicker, exchangeRate)
        );
    }
}
