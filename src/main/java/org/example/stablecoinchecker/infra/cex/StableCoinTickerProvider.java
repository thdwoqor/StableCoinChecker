package org.example.stablecoinchecker.infra.cex;

import java.util.List;

public interface StableCoinTickerProvider {

    List<StableCoinTickerResponse> getStableCoinTickers();
}
