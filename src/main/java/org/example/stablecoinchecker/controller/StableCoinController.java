package org.example.stablecoinchecker.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.service.StableCoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StableCoinController {

    private final StableCoinService service;

    @PostMapping("/area")
    public ResponseEntity<List<StableCoinResponse>> findArea(
            @RequestParam("cex") String cex,
            @RequestParam("symbol") String symbol
    ) {
        return ResponseEntity.ok(service.findArea(cex, symbol));
    }
}
