package org.example.stablecoinchecker.service;

import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.when;

import java.math.BigDecimal;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.example.stablecoinchecker.infra.exchangerate.coincodex.CoinCodexExchangeRateClient;
import org.example.stablecoinchecker.infra.exchangerate.coincodex.dto.CoinCodexExchangeRateResponse;
import org.example.stablecoinchecker.infra.exchangerate.coincodex.dto.FiatRates;
import org.example.stablecoinchecker.infra.exchangerate.manana.MananaExchangeRateClient;
import org.example.stablecoinchecker.infra.exchangerate.manana.dto.MananaExchangeRateResponse;
import org.example.stablecoinchecker.service.ExchangeRateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ExchangeRateServiceTest {


    @MockBean
    private MananaExchangeRateClient mananaExchangeRateClient;
    @MockBean
    private CoinCodexExchangeRateClient coinCodexExchangeRateClient;
    @Autowired
    private ExchangeRateService exchangeRateService;

    @Test
    void 환율_정보_조회_예외_발생시_Recover_테스트() {
        //given-when
        BigDecimal result = new BigDecimal("1383.25");
        when(mananaExchangeRateClient.getExchangeRate())
                .thenReturn(List.of(new MananaExchangeRateResponse("KRW=X", new BigDecimal("1382.39"), 1721000000L)));
        when(coinCodexExchangeRateClient.getExchangeRate())
                .thenReturn(new CoinCodexExchangeRateResponse(new FiatRates(result)));
        BigDecimal exchangeRate = exchangeRateService.getExchangeRate();

        //then
        verify(mananaExchangeRateClient, times(2)).getExchangeRate();
        verify(coinCodexExchangeRateClient, times(1)).getExchangeRate();
        Assertions.assertThat(exchangeRate).isEqualTo(result);
    }
}
