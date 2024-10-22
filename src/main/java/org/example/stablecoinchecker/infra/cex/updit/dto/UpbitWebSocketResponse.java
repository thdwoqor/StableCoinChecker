package org.example.stablecoinchecker.infra.cex.updit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpbitWebSocketResponse {

    @NotNull
    @JsonProperty("trade_price")
    private BigDecimal tradePrice;
    @NotNull
    @JsonProperty("timestamp")
    private Long timestamp;
    @NotNull
    private String code;
}
