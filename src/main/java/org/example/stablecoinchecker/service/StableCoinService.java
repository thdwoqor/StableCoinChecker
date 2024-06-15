package org.example.stablecoinchecker.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stablecoinchecker.domain.cryptopair.CryptoPair;
import org.example.stablecoinchecker.domain.stablecoin.StableCoin;
import org.example.stablecoinchecker.domain.stablecoin.StableCoinRepository;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.infra.cex.CryptoExchangeClient;
import org.example.stablecoinchecker.infra.cex.TickerResponse;
import org.example.stablecoinchecker.service.dto.StableCoinMapper;
import org.example.stablecoinchecker.service.dto.StableCoinSearchCondition;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StableCoinService {

    private final List<CryptoExchangeClient> cryptoExchangeClients;
    private final StableCoinRepository repository;
    private final CryptoPairService cryptoPairService;

    public List<StableCoin> saveAll(final BigDecimal exchangeRate) {
        List<StableCoin> coins = new ArrayList<>();
        for (CryptoExchangeClient client : cryptoExchangeClients) {
            List<CryptoPair> cryptoPairs = cryptoPairService.findByCryptoExchange(findCryptoExchange(client));

            for (CryptoPair cryptoPair : cryptoPairs) {
                try {
                    TickerResponse response = client.getTickers(
                            cryptoPair.getCryptoSymbol().getName()
                    );
                    coins.add(StableCoinMapper.toStableCoin(response, exchangeRate));
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
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

    public List<List<Long>> findStableCoinResponse(
            final String cex,
            final String symbol,
            final Long interval,
            final Long limit,
            final Long to
    ) {
        List<StableCoin> search = repository.search(new StableCoinSearchCondition(
                cex,
                symbol,
                interval,
                limit,
                to
        ));

        List<List<Long>> result = search.stream().map(
                stableCoin -> List.of(
                        stableCoin.getCreatedAt().longValue(),
                        stableCoin.getTicker().getCurrentPrice().longValue()
                )
        ).toList();

        return result;
    }
}
