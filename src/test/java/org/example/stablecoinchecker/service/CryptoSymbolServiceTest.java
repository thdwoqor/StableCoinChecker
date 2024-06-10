package org.example.stablecoinchecker.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.example.stablecoinchecker.DatabaseClearExtension;
import org.example.stablecoinchecker.domain.CryptoSymbol;
import org.example.stablecoinchecker.domain.CryptoSymbolRepository;
import org.example.stablecoinchecker.service.dto.CryptoSymbolRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(DatabaseClearExtension.class)
class CryptoSymbolServiceTest {

    @Autowired
    private CryptoSymbolService cryptoSymbolService;
    @Autowired
    private CryptoSymbolRepository cryptoSymbolRepository;

    @Test
    void 중복된_페어를_저장시_예외가_발생한다() {
        //given
        CryptoSymbol symbol = new CryptoSymbol("USDT", "https://cryptologos.cc/logos/tether-usdt-logo.png");
        cryptoSymbolRepository.save(symbol);

        //then
        assertThatThrownBy(() -> cryptoSymbolService.save(
                new CryptoSymbolRequest("USDT", "https://cryptologos.cc/logos/tether-usdt-logo.png")
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 심볼은 저장할 수 없습니다.");
    }

    @Test
    void 중복된_페어로_수정시_예외가_발생한다() {
        //given
        CryptoSymbol USDT = new CryptoSymbol("USDT", "https://cryptologos.cc/logos/tether-usdt-logo.png");
        cryptoSymbolRepository.save(USDT);
        CryptoSymbol USDC = new CryptoSymbol("USDC", "https://cryptologos.cc/logos/usd-coin-usdc-logo.png");
        CryptoSymbol target = cryptoSymbolRepository.save(USDC);

        //then
        assertThatThrownBy(() -> cryptoSymbolService.edit(
                        target.getId(),
                        new CryptoSymbolRequest("USDT", "https://cryptologos.cc/logos/tether-usdt-logo.png")
                ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 심볼은 저장할 수 없습니다.");
    }
}
