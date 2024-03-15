package org.example.stablecoinchecker.infra.coinone;

import org.example.stablecoinchecker.infra.coinone.dto.CoinonePriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "Coinone", url = "https://api.coinone.co.kr/public")
public interface CoinoneClient {
    @GetMapping("/v2/ticker_new/KRW/USDT?additional_data=false")
    CoinonePriceResponse getTokenPrice();
}
