package org.example.stablecoinchecker.domain.candlestick;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandlestickRepository extends JpaRepository<Candlestick, CandlestickId> {

    Optional<Candlestick> findByCandlestickId(final CandlestickId candlestickId);
}
