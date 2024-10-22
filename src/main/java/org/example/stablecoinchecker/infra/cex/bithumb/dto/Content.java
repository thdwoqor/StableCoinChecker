package org.example.stablecoinchecker.infra.cex.bithumb.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Content {
    private String symbol;
    private String date;
    private String time;
    private BigDecimal closePrice;
}
