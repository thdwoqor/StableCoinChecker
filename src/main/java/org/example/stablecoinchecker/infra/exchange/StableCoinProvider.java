package org.example.stablecoinchecker.infra.exchange;

import java.util.List;

public interface StableCoinProvider {

    List<StableCoinResponse> getStableCoin();
}
