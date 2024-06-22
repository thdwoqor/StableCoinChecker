package org.example.stablecoinchecker.domain.cryptoticker;

import java.util.List;
import org.example.stablecoinchecker.service.dto.CryptoTickerSearchCondition;

public interface CryptoTickerRepositoryCustom {

    List<CryptoTicker> search(final CryptoTickerSearchCondition condition);
}
