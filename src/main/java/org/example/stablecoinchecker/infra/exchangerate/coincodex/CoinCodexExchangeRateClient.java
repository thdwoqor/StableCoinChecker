package org.example.stablecoinchecker.infra.exchangerate.coincodex;

import org.example.stablecoinchecker.infra.exchangerate.coincodex.dto.CoinCodexExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "CoinCodex", url = "https://coincodex.com")
public interface CoinCodexExchangeRateClient {
    @GetMapping("/api/coincodex/get_metadata")
    CoinCodexExchangeRateResponse getExchangeRate();
}
