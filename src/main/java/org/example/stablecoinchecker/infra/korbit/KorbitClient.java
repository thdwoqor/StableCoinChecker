package org.example.stablecoinchecker.infra.korbit;

import org.example.stablecoinchecker.infra.korbit.dto.KorbitPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "Korbit", url = "https://api.korbit.co.kr")
public interface KorbitClient {
    @GetMapping("/v1/ticker/detailed?currency_pair=usdc_krw")
    KorbitPriceResponse getTokenPrice();
}
