package org.example.stablecoinchecker.controller;

import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.service.CryptoSymbolService;
import org.example.stablecoinchecker.service.dto.CryptoSymbolRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CryptoSymbolController {

    private final CryptoSymbolService cryptoSymbolService;

    @PostMapping("/admin/symbols")
    public ResponseEntity<Void> save(@RequestBody CryptoSymbolRequest request) {
        cryptoSymbolService.save(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/admin/symbols/{symbolId}")
    public ResponseEntity<Void> edit(@PathVariable Long symbolId, @RequestBody CryptoSymbolRequest request) {
        cryptoSymbolService.edit(symbolId, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/admin/symbols/{symbolId}")
    public ResponseEntity<Void> delete(@PathVariable Long symbolId) {
        cryptoSymbolService.delete(symbolId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
