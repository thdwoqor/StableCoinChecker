package org.example.stablecoinchecker.domain.exchangeRate;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.stablecoinchecker.domain.BaseEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeRate extends BaseEntity {

    private BigDecimal value;
    private Long createdAt;

    public ExchangeRate(final BigDecimal value) {
        this.value = value;
    }

    @PrePersist
    private void setCreatedAt() {
        createdAt = Instant.now().truncatedTo(ChronoUnit.MINUTES).toEpochMilli();
    }
}
