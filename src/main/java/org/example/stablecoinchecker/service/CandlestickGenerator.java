package org.example.stablecoinchecker.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.domain.candlestick.Candlestick;
import org.example.stablecoinchecker.domain.candlestick.CandlestickId;
import org.example.stablecoinchecker.domain.candlestick.CandlestickRepository;
import org.example.stablecoinchecker.domain.candlestick.CryptoExchange;
import org.example.stablecoinchecker.domain.candlestick.TimeInterval;
import org.example.stablecoinchecker.infra.NamedLockWithJdbcTemplate;
import org.example.stablecoinchecker.infra.cex.CryptoExchangeTickerEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CandlestickGenerator {

    private static final int FIVE_SECONDS = 5;
    private final CandlestickRepository candlestickRepository;
    private final NamedLockWithJdbcTemplate template;

    @EventListener
    public void candleStickGeneration(final CryptoExchangeTickerEvent event) {
        for (TimeInterval timeInterval : TimeInterval.values()) {
            CandlestickId id = CandlestickId.from(
                    CryptoExchange.from(event.identifier()),
                    event.symbol(),
                    timeInterval,
                    event.timestamp()
            );

            template.executeWithNamedLock(getNamedLockKey(id), FIVE_SECONDS, () -> {
                Optional<Candlestick> findCandlestick = candlestickRepository.findByCandlestickId(id);
                if (findCandlestick.isPresent()) {
                    Candlestick candlestick = findCandlestick.get();
                    candlestick.update(event.price());
                } else {
                    candlestickRepository.save(Candlestick.createNew(
                            id,
                            event.price()
                    ));
                }
            });
        }
    }

    private String getNamedLockKey(final CandlestickId id) {
        return id.getCryptoExchange().toString() +
                id.getSymbol() +
                id.getTimeInterval().toString() +
                id.getTimeInterval().toString();
    }

}
