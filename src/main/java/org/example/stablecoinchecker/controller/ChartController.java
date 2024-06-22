package org.example.stablecoinchecker.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.domain.cryptoticker.CryptoTicker;
import org.example.stablecoinchecker.service.CryptoTickerService;
import org.example.stablecoinchecker.service.dto.CryptoTickerSearchCondition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChartController {

    private final CryptoTickerService service;

    @GetMapping("/chart/area")
    public ResponseEntity<List<List<Long>>> getAreaChartData(
            @ModelAttribute CryptoTickerSearchCondition condition
    ) {
        List<CryptoTicker> cryptoTickers = service.searchStableCoins(condition);
        return ResponseEntity.ok(formatAreaChart(cryptoTickers));
    }

    private List<List<Long>> formatAreaChart(final List<CryptoTicker> cryptoTickers) {
        return cryptoTickers.stream().map(
                stableCoin -> List.of(
                        stableCoin.getCreatedAt(),
                        stableCoin.getPrice().getClose().longValue()
                )
        ).toList();
    }
}
