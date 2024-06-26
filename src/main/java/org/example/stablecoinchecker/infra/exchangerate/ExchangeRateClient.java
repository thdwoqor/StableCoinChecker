package org.example.stablecoinchecker.infra.exchangerate;

import java.util.List;
import org.example.stablecoinchecker.infra.exchangerate.dto.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ExchangeRate", url = "https://api.manana.kr")
public interface ExchangeRateClient {
    @GetMapping("/exchange/rate.json?base=KRW&code=USD")
    List<ExchangeRateResponse> getExchangeRate();
}
