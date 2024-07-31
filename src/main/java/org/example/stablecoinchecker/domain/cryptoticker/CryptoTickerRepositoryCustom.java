package org.example.stablecoinchecker.domain.cryptoticker;

import java.util.List;

public interface CryptoTickerRepositoryCustom {

    List<CryptoTicker> search(final CryptoTickerSearchCondition condition);
}
