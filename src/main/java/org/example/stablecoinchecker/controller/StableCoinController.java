package org.example.stablecoinchecker.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.domain.stablecoin.StableCoin;
import org.example.stablecoinchecker.service.StableCoinService;
import org.example.stablecoinchecker.service.dto.StableCoinSearchCondition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StableCoinController {

    private final StableCoinService service;

    @GetMapping("/chart/area")
    public ResponseEntity<List<List<Long>>> getAreaChartData(
            @ModelAttribute StableCoinSearchCondition condition
    ) {
        List<StableCoin> stableCoins = service.searchStableCoins(condition);
        return ResponseEntity.ok(formatAreaChart(stableCoins));
    }

    private List<List<Long>> formatAreaChart(final List<StableCoin> stableCoins) {
        return stableCoins.stream().map(
                stableCoin -> List.of(
                        stableCoin.getCreatedAt(),
                        stableCoin.getTicker().getCurrentPrice().longValue()
                )
        ).toList();
    }
}
