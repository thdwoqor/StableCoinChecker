package org.example.stablecoinchecker.infra;

import org.example.stablecoinchecker.infra.dto.CategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "CoinMarketCap", url = "https://pro-api.coinmarketcap.com")
public interface CoinMarketCapApiClient {

    @GetMapping(value = "/v1/cryptocurrency/category")
    CategoryResponse getCategory(@RequestParam("id") String categoryId, @RequestParam("limit") int limit);
}
