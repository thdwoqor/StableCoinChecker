package org.example.stablecoinchecker.infra.cex.updit;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.example.stablecoinchecker.infra.cex.StableCoin;
import org.example.stablecoinchecker.service.StableCoinRequester;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UpbitAdapterTest {

    @Autowired
    private StableCoinRequester stableCoinRequester;

    @Test
    void 스테이블_코인_데이터_요청_테스트() {
        //given
        List<StableCoin> stableCoins = stableCoinRequester.getStableCoins();

        //when-then
        Assertions.assertThat(stableCoins)
                .usingRecursiveFieldByFieldElementComparator()
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("close", "timestamp")
                .contains(
                        new StableCoin("UPBIT", "USDT", null, null),
                        new StableCoin("KORBIT", "USDT", null, null),
                        new StableCoin("GOPAX", "USDT", null, null),
                        new StableCoin("COINONE", "USDT", null, null),
                        new StableCoin("BITHUMB", "USDT", null, null)
                );
    }
}
