package org.example.stablecoinchecker.infra.cex.bithumb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties
public class Content {
    private String symbol;
    private String date;
    private String time;
    private BigDecimal closePrice;

    public Content(
            final String symbol,
            final String date,
            final String time,
            final BigDecimal closePrice
    ) {
        this.symbol = symbol;
        this.date = date;
        this.time = time;
        this.closePrice = closePrice;
    }

}
