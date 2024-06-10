package org.example.stablecoinchecker.controller;

import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.service.CryptoPairService;
import org.example.stablecoinchecker.service.CryptoSymbolService;
import org.example.stablecoinchecker.service.dto.CryptoPairRequest;
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
public class CryptoPairController {

    private final CryptoPairService cryptoPairService;

    @PostMapping("/admin/pairs")
    public ResponseEntity<Void> save(@RequestBody CryptoPairRequest request) {
        cryptoPairService.save(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/admin/pairs/{pairId}")
    public ResponseEntity<Void> edit(@PathVariable Long pairId, @RequestBody CryptoPairRequest request) {
        cryptoPairService.edit(pairId, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/admin/pairs/{pairId}")
    public ResponseEntity<Void> delete(@PathVariable Long pairId) {
        cryptoPairService.delete(pairId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
