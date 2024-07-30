package org.example.stablecoinchecker.domain.cryptopair;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.domain.cryptosymbol.CryptoSymbol;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DuplicateCryptoPairValidator implements CryptoPairValidator {

    private final CryptoPairRepository cryptoPairRepository;

    public void validate(final CryptoExchange cryptoExchange, final CryptoSymbol cryptoSymbol) {
        Optional<CryptoPair> findCryptoPair = cryptoPairRepository.findByCryptoExchangeAndCryptoSymbol(
                cryptoExchange, cryptoSymbol
        );
        if (findCryptoPair.isPresent()) {
            throw new IllegalArgumentException("중복된 페어는 저장할 수 없습니다.");
        }
    }
}
