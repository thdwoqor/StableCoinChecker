package org.example.stablecoinchecker.infra.exchangerate.investing;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.exchangerate.investing.dto.InvestingResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvestingClientImpl {

    private final InvestingClient investingClient;

    public BigDecimal getExchangeRate() {
        InvestingResponse response = investingClient.getExchangeRate();
        return BigDecimal.valueOf((Double) response.data().get(response.data().size() - 1).get(4));
    }
}
