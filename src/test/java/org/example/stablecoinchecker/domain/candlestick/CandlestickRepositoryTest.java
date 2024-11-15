package org.example.stablecoinchecker.domain.candlestick;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CandlestickRepositoryTest {

    @Autowired
    private CandlestickRepository candlestickRepository;

    //todo TestContainer 도입 필요
    //https://wildeveloperetrain.tistory.com/281
    @Test
    @Disabled
    void insertOrUpdateAllTest() {
        //given
        Candlestick candlestick = Candlestick.createNew(
                CandlestickId.from(CryptoExchange.BITHUMB, "USDT", TimeInterval.MIN5, 1731045875583L),
                BigDecimal.valueOf(1100)
        );
        Candlestick persistCandlestick = Candlestick.createNew(
                CandlestickId.from(CryptoExchange.BITHUMB, "USDT", TimeInterval.MIN1, 1731045875583L),
                BigDecimal.valueOf(1100)
        );
        candlestickRepository.save(persistCandlestick);

        //when
        candlestick.update(BigDecimal.valueOf(800));
        candlestick.update(BigDecimal.valueOf(1000));
        persistCandlestick.update(BigDecimal.valueOf(900));
        persistCandlestick.update(BigDecimal.valueOf(1200));
        int i = candlestickRepository.insertOrUpdateAll(List.of(candlestick, persistCandlestick));

        //then
        Optional<Candlestick> findCandlestick = candlestickRepository.findById(
                candlestick.getCandlestickId());
        Optional<Candlestick> findPersistCandlestick = candlestickRepository.findById(
                persistCandlestick.getCandlestickId());

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(findCandlestick).isPresent();
            softly.assertThat(findPersistCandlestick).isPresent();
            Candlestick candlestickResult = findCandlestick.get();
            Candlestick persistCandlestickResult = findPersistCandlestick.get();

            softly.assertThat(candlestickResult.getOpen()).isEqualByComparingTo(new BigDecimal(1100));
            softly.assertThat(candlestickResult.getClose()).isEqualByComparingTo(new BigDecimal(1000));
            softly.assertThat(candlestickResult.getHigh()).isEqualByComparingTo(new BigDecimal(1100));
            softly.assertThat(candlestickResult.getLow()).isEqualByComparingTo(new BigDecimal(800));

            softly.assertThat(persistCandlestickResult.getOpen()).isEqualByComparingTo(new BigDecimal(1100));
            softly.assertThat(persistCandlestickResult.getClose()).isEqualByComparingTo(new BigDecimal(1200));
            softly.assertThat(persistCandlestickResult.getHigh()).isEqualByComparingTo(new BigDecimal(1200));
            softly.assertThat(persistCandlestickResult.getLow()).isEqualByComparingTo(new BigDecimal(900));
        });

    }
}
