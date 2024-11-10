package org.example.stablecoinchecker.domain.candlestick;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandlestickRepository extends JpaRepository<Candlestick, Long> {

    Optional<Candlestick> findByCodeAndTimestamp(final Code code, final Timestamp timestamp);

}
