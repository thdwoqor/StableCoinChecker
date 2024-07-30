package org.example.stablecoinchecker.domain.cryptopair;

import org.example.stablecoinchecker.domain.cryptosymbol.CryptoSymbol;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;

public interface CryptoPairValidator {

    void validate(final CryptoExchange cryptoExchange, final CryptoSymbol cryptoSymbol);
}
