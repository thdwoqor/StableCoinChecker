package org.example.stablecoinchecker.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;
import org.example.stablecoinchecker.domain.stablecoin.StableCoin;
import org.example.stablecoinchecker.domain.stablecoin.StableCoinRepository;
import org.example.stablecoinchecker.domain.stablecoin.Ticker;
import org.example.stablecoinchecker.service.dto.StableCoinSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CryptoPairServiceTest {

    @MockBean
    private StableCoinRepository repository;
    @Autowired
    private StableCoinService stableCoinService;

    @Test
    void 메서드를_호출하면_처음을_제외한_나머지는_Cache가_인터셉트한다() {
        // given

        StableCoinSearchCondition search = new StableCoinSearchCondition(
                "UPBIT",
                "USDT",
                900L,
                1000L,
                1718592600000L
        );
        given(repository.search(search))
                .willReturn(List.of(
                        new StableCoin(
                                BigDecimal.valueOf(1300),
                                "UPBIT",
                                "USDT",
                                new Ticker(
                                        BigDecimal.valueOf(1406),
                                        BigDecimal.valueOf(1413),
                                        BigDecimal.valueOf(1404),
                                        BigDecimal.valueOf(1414),
                                        BigDecimal.valueOf(3444182)
                                )
                        )));

        // when
        IntStream.range(0, 10).forEach((i) -> stableCoinService.searchStableCoins(
                "UPBIT",
                "USDT",
                900L,
                1000L,
                1718592600000L
        ));

        // then
        verify(repository, times((1))).search(any());
    }
}
