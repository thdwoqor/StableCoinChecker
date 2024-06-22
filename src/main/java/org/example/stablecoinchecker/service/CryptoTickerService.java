package org.example.stablecoinchecker.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stablecoinchecker.domain.cryptopair.CryptoPair;
import org.example.stablecoinchecker.domain.cryptoticker.CryptoTicker;
import org.example.stablecoinchecker.domain.cryptoticker.CryptoTickerRepository;
import org.example.stablecoinchecker.domain.cryptoticker.Price;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.infra.cex.CryptoExchangeClient;
import org.example.stablecoinchecker.infra.cex.TickerResponse;
import org.example.stablecoinchecker.service.dto.CryptoTickerSearchCondition;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CryptoTickerService {

    private static final int WAIT_TIME = 1000;
    private static final int RETRY_COUNT = 3;

    private final List<CryptoExchangeClient> cryptoExchangeClients;
    private final CryptoTickerRepository repository;
    private final CryptoPairService cryptoPairService;

    @CacheEvict(value = "stablecoin")
    public List<CryptoTicker> saveAll(final BigDecimal exchangeRate) {
        List<CryptoTicker> coins = new ArrayList<>();
        for (CryptoExchangeClient client : cryptoExchangeClients) {
            List<CryptoPair> cryptoPairs = cryptoPairService.findByCryptoExchange(findCryptoExchange(client));

            for (CryptoPair cryptoPair : cryptoPairs) {
                getTickerResponse(client, cryptoPair).ifPresent(
                        response -> coins.add(tickerResponseToCryptoTicker(response, exchangeRate))
                );
            }
        }
        repository.saveAll(coins);
        return coins;
    }

    private CryptoExchange findCryptoExchange(final CryptoExchangeClient client) {
        return Arrays.stream(CryptoExchange.values())
                .filter(client::supports)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 클라이언트입니다."));
    }

    private Optional<TickerResponse> getTickerResponse(
            final CryptoExchangeClient client,
            final CryptoPair cryptoPair
    ) {
        for (int i = 0; i < RETRY_COUNT; i++) {
            try {
                TickerResponse response = client.getTickers(cryptoPair.getCryptoSymbol().getName());
                return Optional.of(response);
            } catch (Exception e) {
                log.error(e.getMessage());
                waitForOneSecond();
            }
        }
        return Optional.empty();
    }

    private void waitForOneSecond() {
        try {
            Thread.sleep(WAIT_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException("작업을 기다리는 도중에 문제가 발생했습니다", e);
        }
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "stablecoin",
            key = "#condition.cex + ':' + "
                    + "#condition.symbol + ':' + "
                    + "#condition.interval + ':' + "
                    + "#condition.limit + ':' + (#condition.to - (#condition.to % (#condition.interval * 1000)))"
    )
    public List<CryptoTicker> searchStableCoins(final CryptoTickerSearchCondition condition) {
        return repository.search(condition);
    }

    public CryptoTicker tickerResponseToCryptoTicker(final TickerResponse response, final BigDecimal exchangeRate) {
        Price price = new Price(response.close());

        return new CryptoTicker(
                exchangeRate,
                response.cex(),
                response.symbol(),
                price
        );
    }
}
