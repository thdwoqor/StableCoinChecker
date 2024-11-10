package org.example.stablecoinchecker.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.domain.candlestick.Candlestick;
import org.example.stablecoinchecker.domain.candlestick.CandlestickRepository;
import org.example.stablecoinchecker.domain.candlestick.Code;
import org.example.stablecoinchecker.domain.candlestick.CryptoExchange;
import org.example.stablecoinchecker.domain.candlestick.Symbol;
import org.example.stablecoinchecker.domain.candlestick.TimeInterval;
import org.example.stablecoinchecker.domain.candlestick.Timestamp;
import org.example.stablecoinchecker.infra.cex.CryptoExchangeTickerEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CandlestickGenerator {

    private final CandlestickRepository candlestickRepository;

    @EventListener
    @Transactional
    public void candleStickGeneration(final CryptoExchangeTickerEvent event) {
        for (TimeInterval timeInterval : TimeInterval.values()) {
            Timestamp timestamp = new Timestamp(timeInterval, event.timestamp());
            Code code = new Code(CryptoExchange.from(event.identifier()), Symbol.from(event.symbol()));

            Optional<Candlestick> findCandlestick = candlestickRepository.findByCodeAndTimestamp(code, timestamp);

            if (findCandlestick.isPresent()) {
                Candlestick candlestick = findCandlestick.get();
                candlestick.update(event.price());
            } else {
                candlestickRepository.save(Candlestick.createNew(
                        code,
                        event.price(),
                        timestamp
                ));
            }
        }

    }

}
