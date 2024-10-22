package org.example.stablecoinchecker.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.domain.candlestick.Candlestick;
import org.example.stablecoinchecker.domain.candlestick.CandlestickRepository;
import org.example.stablecoinchecker.domain.candlestick.Code;
import org.example.stablecoinchecker.domain.candlestick.CryptoExchange;
import org.example.stablecoinchecker.domain.candlestick.Symbol;
import org.example.stablecoinchecker.infra.cex.CryptoExchangeTickerEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandlestickGenerator {

    private final CandlestickRepository candlestickRepository;
    private final Map<Code, Candlestick> messagingBroker = new ConcurrentHashMap<>();

    @Async
    @EventListener
    public void candleStickGeneration(final CryptoExchangeTickerEvent event) {
        if (messagingBroker.containsKey(
                new Code(CryptoExchange.from(event.identifier()), Symbol.from(event.symbol()))
        )) {
            handleExistingCandlestick(event);
        } else {
            createNewCandlestick(event);
        }
    }

    private void handleExistingCandlestick(CryptoExchangeTickerEvent event) {
        Candlestick candlestick = messagingBroker.get(
                new Code(CryptoExchange.from(event.identifier()), Symbol.from(event.symbol()))
        );

        if (isExpired(candlestick, event)) {
            candlestickRepository.save(candlestick);
            resetCandlestick(event);
        } else {
            candlestick.update(event.price());
        }
    }

    private boolean isExpired(Candlestick candlestick, CryptoExchangeTickerEvent event) {
        return candlestick.getTimestamp() < event.timestamp();
    }

    private void resetCandlestick(CryptoExchangeTickerEvent event) {
        messagingBroker.remove(new Code(CryptoExchange.from(event.identifier()), Symbol.from(event.symbol())));
        createNewCandlestick(event);
    }

    private void createNewCandlestick(CryptoExchangeTickerEvent event) {
        messagingBroker.put(new Code(CryptoExchange.from(event.identifier()), Symbol.from(event.symbol())),
                Candlestick.oneMinute(
                        new Code(CryptoExchange.from(event.identifier()), Symbol.from(event.symbol())),
                        event.price(),
                        event.timestamp()
                )
        );
    }
}
