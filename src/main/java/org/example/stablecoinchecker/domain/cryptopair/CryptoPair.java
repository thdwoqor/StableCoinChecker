package org.example.stablecoinchecker.domain.cryptopair;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.stablecoinchecker.domain.BaseEntity;
import org.example.stablecoinchecker.domain.cryptosymbol.CryptoSymbol;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CryptoPair extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    private CryptoExchange cryptoExchange;
    @ManyToOne
    @JoinColumn(name = "crypto_symbol_id")
    private CryptoSymbol cryptoSymbol;

    public CryptoPair(
            final CryptoExchange cryptoExchange,
            final CryptoSymbol cryptoSymbol,
            final CryptoPairValidator validator
    ) {
        validator.validate(cryptoExchange, cryptoSymbol);
        this.cryptoExchange = cryptoExchange;
        this.cryptoSymbol = cryptoSymbol;
    }

    public void update(
            final CryptoExchange cryptoExchange,
            final CryptoSymbol cryptoSymbol,
            final CryptoPairValidator validator
    ) {
        validator.validate(cryptoExchange, cryptoSymbol);
        this.cryptoExchange = cryptoExchange;
        this.cryptoSymbol = cryptoSymbol;
    }
}
