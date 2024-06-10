package org.example.stablecoinchecker.domain.cryptopair;

import java.util.List;
import java.util.Optional;
import org.example.stablecoinchecker.domain.cryptosymbol.CryptoSymbol;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoPairRepository extends JpaRepository<CryptoPair, Long> {

    Optional<CryptoPair> findByCryptoExchangeAndCryptoSymbol(
            final CryptoExchange cryptoExchange,
            final CryptoSymbol cryptoSymbol
    );

    List<CryptoPair> findByCryptoExchange(final CryptoExchange cryptoExchange);

    default CryptoPair getById(final Long id) {
        return findById(id).orElseThrow(() -> new RuntimeException("거래쌍이 존재하지 않습니다."));
    }
}
