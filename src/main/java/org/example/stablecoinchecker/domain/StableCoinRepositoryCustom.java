package org.example.stablecoinchecker.domain;

import java.util.List;
import org.example.stablecoinchecker.service.dto.StableCoinSearchCondition;

public interface StableCoinRepositoryCustom {

    List<StableCoin> search(final StableCoinSearchCondition condition);
}
