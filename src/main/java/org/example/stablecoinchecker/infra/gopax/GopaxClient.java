package org.example.stablecoinchecker.infra.gopax;

import org.example.stablecoinchecker.infra.gopax.dto.GopaxPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "Gopax", url = "https://api.gopax.co.kr")
public interface GopaxClient {
    @GetMapping("/trading-pairs/USDC-KRW/ticker")
    GopaxPriceResponse getTokenPrice();
}
