package org.example.stablecoinchecker.infra.coinmarketcap.dto;

import java.util.List;

public record Data(
        List<Token> coins
) {
}
