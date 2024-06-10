package org.example.stablecoinchecker.domain.cryptosymbol;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.stablecoinchecker.domain.BaseEntity;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CryptoSymbol extends BaseEntity {

    @Column(unique = true, nullable = false, length = 30)
    private String name;
    private String imgUrl;

    public CryptoSymbol(
            final String name,
            final String imgUrl,
            final DuplicateCryptoSymbolValidator validator
    ) {
        validator.validate(name);
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public void update(
            final String name,
            final String imgUrl,
            final DuplicateCryptoSymbolValidator validator
    ) {
        validator.validate(name);
        this.name = name;
        this.imgUrl = imgUrl;
    }
}
