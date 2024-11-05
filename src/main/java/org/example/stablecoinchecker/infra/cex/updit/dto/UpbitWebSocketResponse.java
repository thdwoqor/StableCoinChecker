package org.example.stablecoinchecker.infra.cex.updit.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.Getter;

@Getter
public class UpbitWebSocketResponse {

    private BigDecimal tradePrice;
    private Long timestamp;
    private String code;

    @JsonCreator
    public UpbitWebSocketResponse(
            @JsonProperty(value = "trade_price", required = true) BigDecimal tradePrice,
            @JsonProperty(value = "timestamp", required = true) Long timestamp,
            @JsonProperty(value = "code", required = true) String code
    ) {
        this.tradePrice = tradePrice;
        this.timestamp = timestamp;
        this.code = code;
    }
}
