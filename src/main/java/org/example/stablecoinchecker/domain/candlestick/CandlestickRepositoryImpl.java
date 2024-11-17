package org.example.stablecoinchecker.domain.candlestick;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class CandlestickRepositoryImpl implements CandlestickRepositoryCustom {

    private static final String BULK_INSERT_PREFIX = "insert into candlestick (close,high,low,open,crypto_exchange,symbol,time_interval,timestamp) values ";
    private static final String BULK_INSERT_VALUE = "(%f, %f, %f, %f, '%s', '%s', '%s', %d)";
    private static final String BULK_INSERT_SUFFIX = " ON DUPLICATE KEY UPDATE close = VALUES(close), high = VALUES(high), low = VALUES(low);";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public int insertOrUpdateAll(final List<Candlestick> candlesticks) {
        if (candlesticks.isEmpty()) {
            return 0;
        }

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(BULK_INSERT_PREFIX);

        for (int i = 0; i < candlesticks.size(); i++) {
            Candlestick candlestick = candlesticks.get(i);
            sqlBuilder.append(String.format(
                    BULK_INSERT_VALUE,
                    candlestick.getClose(),
                    candlestick.getHigh(),
                    candlestick.getLow(),
                    candlestick.getOpen(),
                    candlestick.getCandlestickId().getCryptoExchange().toString(),
                    candlestick.getCandlestickId().getSymbol(),
                    candlestick.getCandlestickId().getTimeInterval().toString(),
                    candlestick.getCandlestickId().getTimestamp()
            ));

            if (i != candlesticks.size() - 1) {
                sqlBuilder.append(", ");
            }
        }

        sqlBuilder.append(BULK_INSERT_SUFFIX);

        return jdbcTemplate.update(sqlBuilder.toString());
    }

}
