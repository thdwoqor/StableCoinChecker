package org.example.stablecoinchecker.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stablecoinchecker.domain.CryptoPair;
import org.example.stablecoinchecker.domain.StableCoin;
import org.example.stablecoinchecker.domain.StableCoinRepository;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.infra.cex.CryptoExchangeClient;
import org.example.stablecoinchecker.infra.cex.TickerResponse;
import org.example.stablecoinchecker.service.dto.StableCoinMapper;
import org.example.stablecoinchecker.service.dto.StableCoinResponse;
import org.example.stablecoinchecker.service.dto.StableCoinSearchCondition;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter;

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

    public List<StableCoinResponse> findStableCoinResponse(
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

        return search.stream()
                .map(StableCoinResponse::of)
                .toList();
    }
}
