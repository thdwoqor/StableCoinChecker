package org.example.stablecoinchecker.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.service.StableCoinService;
import org.example.stablecoinchecker.service.dto.StableCoinResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StableCoinController {

    private final StableCoinService service;

    @GetMapping("/area")
    public ResponseEntity<List<StableCoinResponse>> findArea(
            @RequestParam("cex") String cex,
            @RequestParam("symbol") String symbol,
            @RequestParam("interval") Long interval,
            @RequestParam("limit") Long limit,
            @RequestParam("to") Long to
    ) {
        return ResponseEntity.ok(service.findStableCoinResponse(cex, symbol, interval, limit, to));
    }
}
