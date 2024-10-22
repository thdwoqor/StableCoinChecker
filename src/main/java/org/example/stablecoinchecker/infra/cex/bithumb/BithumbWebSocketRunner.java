package org.example.stablecoinchecker.infra.cex.bithumb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class BithumbWebSocketRunner {

    private final BithumbWebSocketHandler handler;
    private final WebSocketClient client;

    @EventListener(ApplicationReadyEvent.class)
    public void connect() {
        client.execute(handler, "wss://pubwss.bithumb.com/pub/ws");
    }

}
