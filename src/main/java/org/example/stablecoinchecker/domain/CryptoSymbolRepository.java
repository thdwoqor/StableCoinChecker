package org.example.stablecoinchecker.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoSymbolRepository extends JpaRepository<CryptoSymbol, Long> {

    Optional<CryptoSymbol> findByName(final String name);

    default CryptoSymbol getById(final Long id) {
        return findById(id).orElseThrow(() -> new RuntimeException("심볼이 존재하지 않습니다."));
    }
}
