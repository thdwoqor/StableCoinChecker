package org.example.stablecoinchecker.infra.exchangerate;

import java.util.List;
import org.example.stablecoinchecker.infra.exchangerate.dto.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ExchangeRate", url = "https://quotation-api-cdn.dunamu.com")
public interface ExchangeRateClient {
    @GetMapping("/v1/forex/recent?codes=FRX.KRWUSD")
    List<ExchangeRateResponse> getExchangeRate();
}
