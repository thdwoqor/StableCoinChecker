package org.example.stablecoinchecker.controller;

import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.cex.CryptoExchange;
import org.example.stablecoinchecker.service.CryptoPairService;
import org.example.stablecoinchecker.service.CryptoSymbolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final CryptoPairService cryptoPairService;
    private final CryptoSymbolService cryptoSymbolService;

    @GetMapping("/admin/symbols")
    public String displayCryptoSymbols(Model model) {
        model.addAttribute("symbols", cryptoSymbolService.findAll());
        return "symbol";
    }

    @GetMapping("/admin/pairs")
    public String displayCryptoPairs(Model model) {
        model.addAttribute("pairs", cryptoPairService.findAll());
        model.addAttribute("cryptoExchanges", CryptoExchange.values());
        model.addAttribute("symbols", cryptoSymbolService.findAll());

        return "pair";
    }
}
