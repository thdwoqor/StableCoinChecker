package org.example.stablecoinchecker.infra.coinmarketcap;

import org.example.stablecoinchecker.infra.coinmarketcap.dto.CategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "CoinMarketCap", url = "https://pro-api.coinmarketcap.com")
public interface CoinMarketCapClient {

    @GetMapping(value = "/v1/cryptocurrency/category")
    CategoryResponse getCategory(
            @RequestHeader("X-CMC_PRO_API_KEY") String secret,
            @RequestHeader("Accept") String accept,
            @RequestParam("id") String categoryId,
            @RequestParam("limit") int limit
    );
}
