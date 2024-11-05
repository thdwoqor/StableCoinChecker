package org.example.stablecoinchecker.infra.cex.updit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpbitWebSocketRunner {

    private final UpbitWebSocketHandler handler;
    private final WebSocketClient client;

    @EventListener(ApplicationReadyEvent.class)
    public void connect() {
        client.execute(handler, "wss://api.upbit.com/websocket/v1");
    }

    @Scheduled(fixedRate = 2000)
    public void reconnect() {
        if (handler.isNotConnected()) {
            connect();
        }
    }

}
