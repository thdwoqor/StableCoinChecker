package org.example.stablecoinchecker.infra.cex.gopax;

import org.example.stablecoinchecker.infra.cex.gopax.dto.GopaxTickerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Gopax", url = "https://api.gopax.co.kr")
public interface GopaxClient {
    @GetMapping("/trading-pairs/{symbol}-{currency}/ticker")
    GopaxTickerResponse getTicker(
            @RequestParam("symbol") String symbol,
            @RequestParam("currency") String currency
    );
}
