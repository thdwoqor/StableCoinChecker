package org.example.stablecoinchecker.infra.cex.bithumb.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;
import lombok.Getter;

@Getter
public class Content {
    private String symbol;
    private String date;
    private String time;
    private BigDecimal closePrice;

    public Content(
            @JsonProperty(value = "symbol", required = true)
            final String symbol,
            @JsonProperty(value = "date", required = true)
            final String date,
            @JsonProperty(value = "time", required = true)
            final String time,
            @JsonProperty(value = "closePrice", required = true)
            final BigDecimal closePrice
    ) {
        this.symbol = symbol;
        this.date = date;
        this.time = time;
        this.closePrice = closePrice;
    }

}
