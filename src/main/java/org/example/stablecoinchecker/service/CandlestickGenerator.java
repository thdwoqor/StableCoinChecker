package org.example.stablecoinchecker.service;

import java.math.BigDecimal;
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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandlestickGenerator {

    private final CandlestickRepository candlestickRepository;
    private final Map<Code, Candlestick> messagingBroker = new ConcurrentHashMap<>();

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
            Code code = new Code(CryptoExchange.from(event.identifier()), Symbol.from(event.symbol()));
            Candlestick newCandlestick = Candlestick.oneMinute(code, event.price(), event.timestamp());
            candlestickRepository.save(newCandlestick); // 새로운 객체로 저장
            messagingBroker.put(code, newCandlestick);
        } else {
            // 기존 캔들스틱 업데이트
            candlestick.update(event.price());
        }
    }

    private boolean isExpired(Candlestick candlestick, CryptoExchangeTickerEvent event) {
        return candlestick.getTimestamp() < event.timestamp();
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
