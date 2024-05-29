package org.example.stablecoinchecker.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stablecoinchecker.domain.StableCoin;
import org.example.stablecoinchecker.infra.cex.CryptoPairs;
import org.example.stablecoinchecker.infra.cex.CexClient;
import org.example.stablecoinchecker.infra.cex.TickerResponse;
import org.example.stablecoinchecker.service.dto.StableCoinMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StableCoinService {

    private final List<CexClient> cexClients;

    public List<StableCoin> findStableCoin(final BigDecimal exchangeRate) {
        ArrayList<StableCoin> coins = new ArrayList<>();
        for (CexClient client : cexClients) {
            for (CryptoPairs cryptoPair : client.getCryptoPairs()) {
                try {
                    TickerResponse response = client.getTickers(
                            cryptoPair.orderCurrency(),
                            cryptoPair.paymentCurrency()
                    );
                    coins.add(StableCoinMapper.toStableCoin(response, exchangeRate));
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
        return coins;
    }
}
