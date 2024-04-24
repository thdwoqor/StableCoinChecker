package org.example.stablecoinchecker.infra.telegram;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MessageRequest {

    private final String message;
}
