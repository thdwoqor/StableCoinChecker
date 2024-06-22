package org.example.stablecoinchecker.domain.cryptoticker;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoTickerRepository extends JpaRepository<CryptoTicker, Long>,
        CryptoTickerRepositoryCustom {
}
