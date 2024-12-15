package org.example.stablecoinchecker.infra.exchangerate.investing.dto;

import java.util.List;

public record InvestingResponse(
        List<List<Object>> data
) {
}

