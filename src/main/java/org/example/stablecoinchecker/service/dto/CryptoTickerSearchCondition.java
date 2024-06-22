package org.example.stablecoinchecker.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CryptoTickerSearchCondition {
    private String cex;
    private String symbol;
    private Long interval;
    private Long limit;
    private Long to;
}
