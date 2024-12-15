package org.example.stablecoinchecker.infra.exchangerate.investing;

import org.example.stablecoinchecker.infra.exchangerate.investing.dto.InvestingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "Investing", url = "https://api.investing.com/api")
public interface InvestingClient {

    @GetMapping(value = "/financialdata/650/historical/chart/?interval=PT1M&pointscount=60",
            headers = {
                    "Referer=https://www.investing.com",
                    "Origin=https://www.investing.com",
                    "Accept=application/json, text/javascript, */*; q=0.01",
                    "X-Requested-With=XMLHttpRequest",
                    "Sec-Fetch-Site=same-origin",
                    "Sec-Fetch-Mode=cors",
                    "User-Agent=PostmanRuntime/7.28.0"
            })
    InvestingResponse getExchangeRate();
}
