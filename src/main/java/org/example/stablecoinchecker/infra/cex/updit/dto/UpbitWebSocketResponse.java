package org.example.stablecoinchecker.infra.cex.updit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties
public class UpbitWebSocketResponse {

    private BigDecimal tradePrice;
    private Long timestamp;
    private String code;

    public UpbitWebSocketResponse(
            BigDecimal tradePrice,
            Long timestamp,
            String code
    ) {
        this.tradePrice = tradePrice;
        this.timestamp = timestamp;
        this.code = code;
    }
}
