package org.example.stablecoinchecker.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.domain.cryptosymbol.CryptoSymbol;
import org.example.stablecoinchecker.domain.cryptosymbol.CryptoSymbolRepository;
import org.example.stablecoinchecker.domain.cryptosymbol.CryptoSymbolValidator;
import org.example.stablecoinchecker.domain.cryptosymbol.DuplicateCryptoSymbolValidator;
import org.example.stablecoinchecker.service.dto.CryptoSymbolRequest;
import org.example.stablecoinchecker.service.dto.CryptoSymbolResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CryptoSymbolService {

    private final CryptoSymbolRepository cryptoSymbolRepository;
    private final CryptoSymbolValidator validator;

    @Transactional(readOnly = true)
    public List<CryptoSymbolResponse> findAll() {
        return cryptoSymbolRepository.findAll()
                .stream()
                .map(CryptoSymbolResponse::of)
                .toList();
    }

    public void save(final CryptoSymbolRequest request) {
        CryptoSymbol cryptoSymbol = new CryptoSymbol(
                request.name(),
                request.imgUrl(),
                validator
        );
        cryptoSymbolRepository.save(cryptoSymbol);
    }

    public void edit(final Long symbolId, final CryptoSymbolRequest request) {
        CryptoSymbol cryptoSymbol = cryptoSymbolRepository.getById(symbolId);
        cryptoSymbol.update(
                request.name(),
                request.imgUrl(),
                validator
        );
    }

    public void delete(final Long symbolId) {
        cryptoSymbolRepository.deleteById(symbolId);
    }
}
