package org.example.stablecoinchecker.service;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.domain.candlestick.Candlestick;
import org.example.stablecoinchecker.domain.candlestick.CandlestickId;
import org.example.stablecoinchecker.domain.candlestick.CandlestickRepository;
import org.example.stablecoinchecker.domain.candlestick.CryptoExchange;
import org.example.stablecoinchecker.domain.candlestick.TimeInterval;
import org.example.stablecoinchecker.infra.NamedLockWithJdbcTemplate;
import org.example.stablecoinchecker.infra.cex.CryptoExchangeTickerEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandlestickGenerator {

    private static final int FIVE_SECONDS = 5;

    private final CandlestickRepository candlestickRepository;
    private final NamedLockWithJdbcTemplate template;

    @EventListener
    @Async("candlestickGeneratorAsyncExecutor")
    public void candleStickGeneration(final CryptoExchangeTickerEvent event) {
        template.executeWithNamedLock(event.identifier() + event.symbol(), FIVE_SECONDS, () -> {
            List<Candlestick> candlesticks = getCandlesticks(event);

            for (TimeInterval timeInterval : TimeInterval.values()) {
                boolean isAlive = false;
                for (Candlestick candlestick : candlesticks) {
                    if (candlestick.getCandlestickId().getTimeInterval() == timeInterval) {
                        isAlive = true;
                        candlestick.update(event.price());
                        candlesticks.add(candlestick);
                        break;
                    }
                }
                if (isAlive == false) {
                    candlesticks.add(Candlestick.createNew(
                            CandlestickId.from(
                                    CryptoExchange.from(event.identifier()),
                                    event.symbol(),
                                    timeInterval,
                                    event.timestamp()
                            ),
                            event.price()
                    ));
                }
            }

            candlestickRepository.insertOrUpdateAll(candlesticks);
        });
    }

    private List<Candlestick> getCandlesticks(final CryptoExchangeTickerEvent event) {
        List<CandlestickId> candlestickIds = Arrays.stream(TimeInterval.values()).map(
                timeInterval -> CandlestickId.from(
                        CryptoExchange.from(event.identifier()),
                        event.symbol(),
                        timeInterval,
                        event.timestamp()
                )
        ).toList();

        return candlestickRepository.findAllById(candlestickIds);
    }

}
