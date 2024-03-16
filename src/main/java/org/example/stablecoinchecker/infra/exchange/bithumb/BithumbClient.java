package org.example.stablecoinchecker.infra.exchange.bithumb;

import org.example.stablecoinchecker.infra.exchange.bithumb.dto.BithumbTickerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Bithumb", url = "https://api.bithumb.com/public")
public interface BithumbClient {

    @GetMapping("/ticker/{symbol}_{currency}")
    BithumbTickerResponse getTicker(
            @RequestParam("symbol") String symbol,
            @RequestParam("currency") String currency
    );
}
