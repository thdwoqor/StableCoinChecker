package org.example.stablecoinchecker.domain.cryptopair;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.example.stablecoinchecker.DatabaseClearExtension;
import org.example.stablecoinchecker.domain.cryptosymbol.CryptoSymbol;
import org.example.stablecoinchecker.domain.cryptosymbol.CryptoSymbolRepository;
import org.example.stablecoinchecker.domain.cryptosymbol.CryptoSymbolValidator;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(DatabaseClearExtension.class)
class CryptoPairTest {

    @Autowired
    private CryptoPairRepository cryptoPairRepository;
    @Autowired
    private CryptoSymbolRepository cryptoSymbolRepository;
    @Autowired
    private CryptoPairValidator cryptoPairValidator;
    @Autowired
    private CryptoSymbolValidator cryptoSymbolValidator;

    @Test
    void 중복된_페어를_저장시_예외가_발생한다() {
        //given
        CryptoSymbol symbol = new CryptoSymbol(
                "USDT",
                "https://cryptologos.cc/logos/tether-usdt-logo.png",
                cryptoSymbolValidator
        );
        cryptoSymbolRepository.save(symbol);
        cryptoPairRepository.save(new CryptoPair(
                CryptoExchange.BITHUMB,
                symbol,
                cryptoPairValidator
        ));

        //then
        assertThatThrownBy(
                () -> new CryptoPair(
                        CryptoExchange.BITHUMB,
                        symbol,
                        cryptoPairValidator
                ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 페어는 저장할 수 없습니다.");
    }

    @Test
    void 중복된_페어로_수정시_예외가_발생한다() {
        //given
        CryptoSymbol USDT = new CryptoSymbol(
                "USDT",
                "https://cryptologos.cc/logos/tether-usdt-logo.png",
                cryptoSymbolValidator
        );
        cryptoSymbolRepository.save(USDT);
        cryptoPairRepository.save(new CryptoPair(
                CryptoExchange.BITHUMB,
                USDT,
                cryptoPairValidator
        ));
        CryptoSymbol USDC = new CryptoSymbol(
                "USDC",
                "https://cryptologos.cc/logos/usd-coin-usdc-logo.png",
                cryptoSymbolValidator
        );
        cryptoSymbolRepository.save(USDC);
        CryptoPair target = cryptoPairRepository.save(new CryptoPair(
                CryptoExchange.BITHUMB,
                USDC,
                cryptoPairValidator
        ));

        //then
        assertThatThrownBy(() -> target.update(CryptoExchange.BITHUMB, USDT, cryptoPairValidator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 페어는 저장할 수 없습니다.");
    }
}
