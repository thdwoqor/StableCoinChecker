package org.example.stablecoinchecker.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.domain.stablecoin.StableCoin;
import org.example.stablecoinchecker.service.StableCoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StableCoinController {

    private final StableCoinService service;

    @GetMapping("/chart/area")
    public ResponseEntity<List<List<Long>>> getAreaChartData(
            @RequestParam("cex") String cex,
            @RequestParam("symbol") String symbol,
            @RequestParam("interval") Long interval,
            @RequestParam("limit") Long limit,
            @RequestParam("to") Long to
    ) {
        List<StableCoin> stableCoins = service.searchStableCoins(cex, symbol, interval, limit, to);
        List<List<Long>> result = stableCoins.stream().map(
                stableCoin -> List.of(
                        stableCoin.getCreatedAt(),
                        stableCoin.getTicker().getCurrentPrice().longValue()
                )
        ).toList();
        return ResponseEntity.ok(result);
    }
}
