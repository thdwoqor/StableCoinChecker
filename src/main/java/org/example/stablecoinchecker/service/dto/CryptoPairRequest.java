package org.example.stablecoinchecker.service.dto;

import org.example.stablecoinchecker.infra.cex.CryptoExchange;

public record CryptoPairRequest(
        CryptoExchange cryptoExchange,
        Long cryptoSymbolId
) {
}
