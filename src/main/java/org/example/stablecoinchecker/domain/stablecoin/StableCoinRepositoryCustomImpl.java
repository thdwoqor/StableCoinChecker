package org.example.stablecoinchecker.domain.stablecoin;

import static org.example.stablecoinchecker.domain.stablecoin.QStableCoin.stableCoin;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import org.example.stablecoinchecker.service.dto.StableCoinSearchCondition;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@AllArgsConstructor
public class StableCoinRepositoryCustomImpl implements StableCoinRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StableCoin> search(final StableCoinSearchCondition condition) {
        List<StableCoin> result = jpaQueryFactory.selectFrom(stableCoin)
                .where(
                        createdAtMod(condition),
                        symbolEq(condition),
                        cryptocurrencyExchangeEq(condition),
                        createdAtLoe(condition)
                )
                .limit(getCondition(condition))
                .orderBy(stableCoin.createdAt.desc())
                .fetch();

        result.sort(Comparator.comparing(StableCoin::getCreatedAt));
        return result;
    }

    private static BooleanExpression cryptocurrencyExchangeEq(final StableCoinSearchCondition condition) {
        return stableCoin.cex.eq(condition.getCex());
    }

    private Long getCondition(final StableCoinSearchCondition condition) {
        return condition.getLimit() != null ? condition.getLimit() : 1000;
    }

    private BooleanExpression createdAtLoe(final StableCoinSearchCondition condition) {
        return condition.getTo() != null ? stableCoin.createdAt.loe(condition.getTo()) : null;
    }

    private BooleanExpression createdAtMod(final StableCoinSearchCondition condition) {
        return condition.getInterval() != null ? stableCoin.createdAt.mod(condition.getInterval()).eq(0L) : null;
    }

    private BooleanExpression symbolEq(final StableCoinSearchCondition condition) {
        return StringUtils.hasText(condition.getSymbol()) ? stableCoin.symbol.eq(condition.getSymbol().toUpperCase()) : null;
    }
}
