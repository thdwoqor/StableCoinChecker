package org.example.stablecoinchecker.domain.cryptosymbol;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DuplicateCryptoSymbolValidator {

    private final CryptoSymbolRepository cryptoSymbolRepository;

    public void validate(final String name) {
        if (cryptoSymbolRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("중복된 심볼은 저장할 수 없습니다.");
        }
    }
}
