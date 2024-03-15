package org.example.stablecoinchecker.infra.bithumb;

import org.example.stablecoinchecker.infra.bithumb.dto.BithumbPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "Bithumb", url = "https://api.bithumb.com/public")
public interface BithumbClient {
    @GetMapping("/ticker/USDT_KRW")
    BithumbPriceResponse getTokenPrice();
}
