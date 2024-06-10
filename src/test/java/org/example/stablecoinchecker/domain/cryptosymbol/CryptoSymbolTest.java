package org.example.stablecoinchecker.domain.cryptosymbol;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.example.stablecoinchecker.DatabaseClearExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(DatabaseClearExtension.class)
class CryptoSymbolTest {

    @Autowired
    private CryptoSymbolRepository cryptoSymbolRepository;
    @Autowired
    private DuplicateCryptoSymbolValidator cryptoSymbolValidator;

    @Test
    void 중복된_심볼을_저장시_예외가_발생한다() {
        //given
        CryptoSymbol symbol = new CryptoSymbol(
                "USDT",
                "https://cryptologos.cc/logos/tether-usdt-logo.png",
                cryptoSymbolValidator
        );
        cryptoSymbolRepository.save(symbol);

        //then
        assertThatThrownBy(() -> new CryptoSymbol(
                "USDT",
                "https://cryptologos.cc/logos/tether-usdt-logo.png",
                cryptoSymbolValidator
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 심볼은 저장할 수 없습니다.");
    }

    @Test
    void 중복된_심볼로_수정시_예외가_발생한다() {
        //given
        CryptoSymbol USDT = new CryptoSymbol(
                "USDT",
                "https://cryptologos.cc/logos/tether-usdt-logo.png",
                cryptoSymbolValidator
        );
        cryptoSymbolRepository.save(USDT);
        CryptoSymbol USDC = new CryptoSymbol(
                "USDC",
                "https://cryptologos.cc/logos/usd-coin-usdc-logo.png",
                cryptoSymbolValidator
        );
        CryptoSymbol target = cryptoSymbolRepository.save(USDC);

        //then
        assertThatThrownBy(() -> target.update(
                "USDC",
                "https://cryptologos.cc/logos/usd-coin-usdc-logo.png",
                cryptoSymbolValidator)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 심볼은 저장할 수 없습니다.");
    }
}
