package org.example.stablecoinchecker.domain.stablecoin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StableCoinRepository extends JpaRepository<StableCoin, Long>, StableCoinRepositoryCustom {
}
