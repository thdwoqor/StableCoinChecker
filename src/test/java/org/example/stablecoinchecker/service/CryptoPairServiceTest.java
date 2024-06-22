package org.example.stablecoinchecker.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;
import org.example.stablecoinchecker.domain.cryptoticker.CryptoTicker;
import org.example.stablecoinchecker.domain.cryptoticker.Price;
import org.example.stablecoinchecker.domain.cryptoticker.CryptoTickerRepository;
import org.example.stablecoinchecker.service.dto.CryptoTickerSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CryptoPairServiceTest {

    @MockBean
    private CryptoTickerRepository repository;
    @Autowired
    private CryptoTickerService cryptoTickerService;

    @Test
    void 메서드를_호출하면_처음을_제외한_나머지는_Cache가_인터셉트한다() {
        // given
        CryptoTickerSearchCondition search = new CryptoTickerSearchCondition(
                "UPBIT",
                "USDT",
                900L,
                1000L,
                1718592600000L
        );
        given(repository.search(search))
                .willReturn(List.of(
                        new CryptoTicker(
                                BigDecimal.valueOf(1300),
                                "UPBIT",
                                "USDT",
                                new Price(
                                        BigDecimal.valueOf(1406)
                                )
                        )));

        // when
        IntStream.range(0, 10).forEach((i) -> cryptoTickerService.searchStableCoins(search));

        // then
        verify(repository, times((1))).search(any());
    }
}
