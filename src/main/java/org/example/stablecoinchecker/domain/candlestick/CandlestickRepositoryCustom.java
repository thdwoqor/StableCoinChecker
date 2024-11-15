package org.example.stablecoinchecker.domain.candlestick;

import java.util.List;

public interface CandlestickRepositoryCustom {

    int insertOrUpdateAll(final List<Candlestick> candlesticks);
}
