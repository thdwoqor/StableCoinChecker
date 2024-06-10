package org.example.stablecoinchecker.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.domain.CryptoSymbol;
import org.example.stablecoinchecker.domain.CryptoSymbolRepository;
import org.example.stablecoinchecker.service.dto.CryptoSymbolRequest;
import org.example.stablecoinchecker.service.dto.CryptoSymbolResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CryptoSymbolService {

    private final CryptoSymbolRepository cryptoSymbolRepository;

    @Transactional(readOnly = true)
    public List<CryptoSymbolResponse> findAll() {
        return cryptoSymbolRepository.findAll()
                .stream()
                .map(CryptoSymbolResponse::of)
                .toList();
    }

    public void save(final CryptoSymbolRequest request) {
        validateDuplicateCryptoSymbol(request);
        CryptoSymbol cryptoSymbol = new CryptoSymbol(request.name(), request.imgUrl());
        cryptoSymbolRepository.save(cryptoSymbol);
    }

    private void validateDuplicateCryptoSymbol(final CryptoSymbolRequest request) {
        if (cryptoSymbolRepository.findByName(request.name()).isPresent()) {
            throw new IllegalArgumentException("중복된 심볼은 저장할 수 없습니다.");
        }
    }

    public void edit(final Long symbolId, final CryptoSymbolRequest request) {
        validateDuplicateCryptoSymbol(request);
        CryptoSymbol cryptoSymbol = cryptoSymbolRepository.getById(symbolId);
        cryptoSymbol.edit(request.name(), request.imgUrl());
    }

    public void delete(final Long symbolId) {
        cryptoSymbolRepository.deleteById(symbolId);
    }
}
