package org.example.stablecoinchecker.domain.cryptoticker;

import static org.example.stablecoinchecker.domain.cryptoticker.QCryptoTicker.cryptoTicker;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@AllArgsConstructor
public class CryptoTickerRepositoryCustomImpl implements CryptoTickerRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CryptoTicker> search(final CryptoTickerSearchCondition condition) {
        List<CryptoTicker> result = jpaQueryFactory.selectFrom(cryptoTicker)
                .where(
                        createdAtMod(condition),
                        symbolEq(condition),
                        cryptocurrencyExchangeEq(condition),
                        createdAtLoe(condition)
                )
                .limit(getCondition(condition))
                .orderBy(cryptoTicker.createdAt.desc())
                .fetch();

        result.sort(Comparator.comparing(CryptoTicker::getCreatedAt));
        return result;
    }

    private static BooleanExpression cryptocurrencyExchangeEq(final CryptoTickerSearchCondition condition) {
        return cryptoTicker.cryptoExchange.eq(condition.getCex());
    }

    private Long getCondition(final CryptoTickerSearchCondition condition) {
        return condition.getLimit() != null ? condition.getLimit() : 1000;
    }

    private BooleanExpression createdAtLoe(final CryptoTickerSearchCondition condition) {
        return condition.getTo() != null ? cryptoTicker.createdAt.loe(condition.getTo()) : null;
    }

    private BooleanExpression createdAtMod(final CryptoTickerSearchCondition condition) {
        return condition.getInterval() != null ? cryptoTicker.createdAt.mod(condition.getInterval()).eq(0L) : null;
    }

    private BooleanExpression symbolEq(final CryptoTickerSearchCondition condition) {
        return StringUtils.hasText(condition.getSymbol()) ? cryptoTicker.symbol.eq(condition.getSymbol().toUpperCase())
                : null;
    }
}
