package org.example.stablecoinchecker.service;

import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.when;

import java.math.BigDecimal;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.example.stablecoinchecker.infra.exchangerate.investing.InvestingClient;
import org.example.stablecoinchecker.infra.exchangerate.investing.dto.InvestingResponse;
import org.example.stablecoinchecker.infra.exchangerate.manana.MananaExchangeRateClient;
import org.example.stablecoinchecker.infra.exchangerate.manana.dto.MananaExchangeRateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ExchangeRateServiceTest {


    @MockBean
    private MananaExchangeRateClient mananaExchangeRateClient;
    @MockBean
    private InvestingClient investingClient;
    @Autowired
    private ExchangeRateService exchangeRateService;

    @Test
    void 환율_정보_조회_예외_발생시_Recover_테스트() {
        //given-when
        when(mananaExchangeRateClient.getExchangeRate())
                .thenReturn(List.of(new MananaExchangeRateResponse("KRW=X", new BigDecimal("1382.39"), 1721000000L)));
        when(investingClient.getExchangeRate())
                .thenReturn(new InvestingResponse(List.of(List.of(new Object()))));
        BigDecimal exchangeRate = exchangeRateService.getExchangeRate();

        //then
        verify(mananaExchangeRateClient, times(1)).getExchangeRate();
        verify(investingClient, times(1)).getExchangeRate();
    }
}
