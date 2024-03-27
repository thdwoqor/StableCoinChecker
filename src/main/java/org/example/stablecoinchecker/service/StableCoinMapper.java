package org.example.stablecoinchecker.service;

import java.math.BigDecimal;
import org.example.stablecoinchecker.domain.StableCoin;
import org.example.stablecoinchecker.infra.cex.StableCoinTicker;
import org.example.stablecoinchecker.infra.telegram.dto.StableCoinInfo;
import org.springframework.util.StringUtils;

public class StableCoinMapper {

    private static StableCoin toStableCoin(final StableCoinTicker response, final BigDecimal exchangeRate) {
        return new StableCoin(
                new BigDecimal(String.valueOf(exchangeRate)),
                response.cex(),
                new BigDecimal(response.price()),
                response.symbol()
        );
    }

    public static StableCoinInfo toStableCoinInfo(
            final StableCoinTicker response, final BigDecimal exchangeRate
    ) {
        StableCoin stableCoin = toStableCoin(response, exchangeRate);
        return new StableCoinInfo(
                StringUtils.capitalize(stableCoin.getCryptocurrencyExchange().toString().toLowerCase()),
                stableCoin.getSymbol(),
                stableCoin.getPrice().intValue(),
                stableCoin.calculateKimchiPremium().doubleValue()
        );
    }
}
