package org.example.stablecoinchecker.infra.cex.updit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UpbitWebSocketResponse {

    @JsonProperty("trade_price")
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
