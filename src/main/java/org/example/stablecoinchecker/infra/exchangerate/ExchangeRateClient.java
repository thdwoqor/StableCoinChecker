package org.example.stablecoinchecker.infra.exchangerate;

import java.util.List;
import org.example.stablecoinchecker.infra.exchangerate.dto.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ExchangeRate", url = "https://coincodex.com")
public interface ExchangeRateClient {
    @GetMapping("/api/coincodex/get_metadata")
    ExchangeRateResponse getExchangeRate();
}
