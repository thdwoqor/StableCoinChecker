package org.example.stablecoinchecker.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.StableCoin;
import org.example.stablecoinchecker.infra.cex.StableCoinProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StableCoinRequester {

    private final List<StableCoinProvider> stableCoinProviders;

    public List<StableCoin> getStableCoins() {
        return stableCoinProviders.stream()
                .flatMap(provider -> provider.getStableCoins().stream())
                .toList();
    }

}
