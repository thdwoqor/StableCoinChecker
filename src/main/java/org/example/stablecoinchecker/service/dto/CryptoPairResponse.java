package org.example.stablecoinchecker.service.dto;

import org.example.stablecoinchecker.domain.cryptopair.CryptoPair;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;

public record CryptoPairResponse(
        Long id,
        CryptoExchange cryptoExchange,
        CryptoSymbolResponse cryptoSymbol
) {

    public static CryptoPairResponse of(final CryptoPair cryptoPair) {
        return new CryptoPairResponse(
                cryptoPair.getId(),
                cryptoPair.getCryptoExchange(),
                CryptoSymbolResponse.of(cryptoPair.getCryptoSymbol())
        );
    }
}
