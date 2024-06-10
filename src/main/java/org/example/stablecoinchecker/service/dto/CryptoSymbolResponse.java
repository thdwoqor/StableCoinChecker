package org.example.stablecoinchecker.service.dto;

import org.example.stablecoinchecker.domain.CryptoSymbol;

public record CryptoSymbolResponse(
        Long id,
        String name,
        String imgUrl
) {

    public static CryptoSymbolResponse of(final CryptoSymbol cryptoSymbol) {
        return new CryptoSymbolResponse(
                cryptoSymbol.getId(),
                cryptoSymbol.getName(),
                cryptoSymbol.getImgUrl()
        );
    }
}
