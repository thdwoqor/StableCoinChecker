package org.example.stablecoinchecker.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.example.stablecoinchecker.DatabaseClearExtension;
import org.example.stablecoinchecker.domain.CryptoPair;
import org.example.stablecoinchecker.domain.CryptoPairRepository;
import org.example.stablecoinchecker.domain.CryptoSymbol;
import org.example.stablecoinchecker.domain.CryptoSymbolRepository;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.service.dto.CryptoPairRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(DatabaseClearExtension.class)
class CryptoPairServiceTest {

    @Autowired
    private CryptoPairService cryptoPairService;
    @Autowired
    private CryptoSymbolRepository cryptoSymbolRepository;
    @Autowired
    private CryptoPairRepository cryptoPairRepository;

    @Test
    void 중복된_페어를_저장시_예외가_발생한다() {
        //given
        CryptoSymbol symbol = new CryptoSymbol("USDT", "https://cryptologos.cc/logos/tether-usdt-logo.png");
        CryptoSymbol persistSymbol = cryptoSymbolRepository.save(symbol);
        cryptoPairService.save(new CryptoPairRequest(CryptoExchange.BITHUMB, persistSymbol.getId()));

        //then
        assertThatThrownBy(
                () -> cryptoPairService.save(new CryptoPairRequest(CryptoExchange.BITHUMB, persistSymbol.getId())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 페어는 저장할 수 없습니다.");
    }

    @Test
    void 중복된_페어로_수정시_예외가_발생한다() {
        //given
        CryptoSymbol USDT = new CryptoSymbol("USDT", "https://cryptologos.cc/logos/tether-usdt-logo.png");
        CryptoSymbol persistUSDT = cryptoSymbolRepository.save(USDT);
        cryptoPairRepository.save(new CryptoPair(CryptoExchange.BITHUMB, persistUSDT));
        CryptoSymbol USDC = new CryptoSymbol("USDC", "https://cryptologos.cc/logos/usd-coin-usdc-logo.png");
        CryptoSymbol persistUSDC = cryptoSymbolRepository.save(USDC);
        CryptoPair target = cryptoPairRepository.save(new CryptoPair(CryptoExchange.BITHUMB, persistUSDC));

        //then
        assertThatThrownBy(() -> cryptoPairService.edit(target.getId(), new CryptoPairRequest(CryptoExchange.BITHUMB, persistUSDT.getId())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 페어는 저장할 수 없습니다.");
    }
}
