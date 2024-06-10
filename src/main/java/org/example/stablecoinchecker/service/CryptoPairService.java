package org.example.stablecoinchecker.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.domain.CryptoPair;
import org.example.stablecoinchecker.domain.CryptoPairRepository;
import org.example.stablecoinchecker.domain.CryptoSymbol;
import org.example.stablecoinchecker.domain.CryptoSymbolRepository;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.service.dto.CryptoPairRequest;
import org.example.stablecoinchecker.service.dto.CryptoPairResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CryptoPairService {

    private final CryptoPairRepository cryptoPairRepository;
    private final CryptoSymbolRepository cryptoSymbolRepository;

    @Transactional(readOnly = true)
    public List<CryptoPairResponse> findAll() {
        return cryptoPairRepository.findAll()
                .stream()
                .map(CryptoPairResponse::of)
                .toList();
    }

    public void save(final CryptoPairRequest request) {
        CryptoSymbol cryptoSymbol = cryptoSymbolRepository.getById(request.cryptoSymbolId());
        validateDuplicateCryptoPair(request, cryptoSymbol);
        cryptoPairRepository.save(new CryptoPair(request.cryptoExchange(), cryptoSymbol));
    }

    public List<CryptoPair> findByCryptoExchange(final CryptoExchange cryptoExchange) {
        return cryptoPairRepository.findByCryptoExchange(cryptoExchange);
    }

    public void edit(final Long pairId, final CryptoPairRequest request) {
        CryptoPair cryptoPair = cryptoPairRepository.getById(pairId);
        CryptoSymbol cryptoSymbol = cryptoSymbolRepository.getById(request.cryptoSymbolId());

        validateDuplicateCryptoPair(request, cryptoSymbol);

        cryptoPair.edit(
                request.cryptoExchange(),
                cryptoSymbol
        );
    }

    private void validateDuplicateCryptoPair(final CryptoPairRequest request, final CryptoSymbol cryptoSymbol) {
        Optional<CryptoPair> findCryptoPair = cryptoPairRepository.findByCryptoExchangeAndCryptoSymbol(
                request.cryptoExchange(), cryptoSymbol
        );
        if (findCryptoPair.isPresent()) {
            throw new IllegalArgumentException("중복된 페어는 저장할 수 없습니다.");
        }
    }

    public void delete(final Long pairId) {
        cryptoPairRepository.deleteById(pairId);
    }
}
