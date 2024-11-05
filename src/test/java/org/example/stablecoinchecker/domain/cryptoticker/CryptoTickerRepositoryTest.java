package org.example.stablecoinchecker.domain.cryptoticker;

import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
import org.example.stablecoinchecker.config.QueryDSLConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Import(QueryDSLConfig.class)
@SuppressWarnings("NonAsciiCharacters")
class CryptoTickerRepositoryTest {

    @Autowired
    private EntityManager em;

//    @Test
//    @Transactional
//    void 생성시간에서_초단위_제거() {
//        //given
//        CryptoTicker cryptoTicker = new CryptoTicker(
//                "UPBIT",
//                "USDT",
//                new Price(
//                        new BigDecimal(1371)
//                )
//        );
//
//        //when
//        em.persist(cryptoTicker);
//
//        //then
//        Assertions.assertThat(cryptoTicker.getCreatedAt() % 60).isZero();
//    }
}
