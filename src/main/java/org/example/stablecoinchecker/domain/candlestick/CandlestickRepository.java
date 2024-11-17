package org.example.stablecoinchecker.domain.candlestick;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CandlestickRepository extends JpaRepository<Candlestick, CandlestickId>, CandlestickRepositoryCustom {
}
